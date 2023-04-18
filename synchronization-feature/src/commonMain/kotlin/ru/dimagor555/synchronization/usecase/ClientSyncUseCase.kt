package ru.dimagor555.synchronization.usecase

import io.github.aakira.napier.Napier
import kotlinx.serialization.json.JsonObject
import ru.dimagor555.encryption.asymmetric.data.key.generateNew
import ru.dimagor555.encryption.asymmetric.domain.AsymmetricDecryptor
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricKeyPair
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import ru.dimagor555.synchronization.domain.key.EncryptedSymmetricKey
import ru.dimagor555.synchronization.domain.key.decrypted
import ru.dimagor555.synchronization.domain.key.encoded
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.ClientSyncStateMachine.Event
import ru.dimagor555.synchronization.usecase.ClientSyncStateMachine.Event.*
import ru.dimagor555.synchronization.usecase.ClientSyncStateMachine.State
import ru.dimagor555.synchronization.usecase.rest.SendEncryptedRequestPasswordsUsecase
import ru.dimagor555.synchronization.usecase.rest.SendEncryptedSyncPasswordRecordsUsecase
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

internal class ClientSyncUseCase(
    setSyncStatus: SetSyncStatusUseCase,
    private val asymmetricDecryptor: AsymmetricDecryptor,
    private val sendPasswordRepository: SendPasswordRepository,
    private val syncPasswordRepository: SyncPasswordRepository,
    private val sendEncryptedSyncPasswordRecordsUsecase: SendEncryptedSyncPasswordRecordsUsecase,
    private val sendEncryptedRequestPasswordsUsecase: SendEncryptedRequestPasswordsUsecase,
) {

    private val stateMachine = ClientSyncStateMachine(setSyncStatus)

    private var asymmetricKeyPair: AsymmetricKeyPair? = null
    private var symmetricKey: SymmetricKey? = null

    sealed interface Result {

        object Success : Result

        object Error : Result
    }

    suspend operator fun invoke(): Result {
        if (stateMachine.state != State.Initial) {
            return Result.Error
        }
        generateAsymmetricKeys() ?: return Result.Error
        val encodedSymmetricKey = sendAsymmetricKey() ?: return Result.Error
        decryptSymmetricKey(encodedSymmetricKey) ?: return Result.Error

        val syncResponse = sendInitialSyncRequest()
        when (syncResponse) {
            is SyncResponse.CommonSyncResponse -> {
                addOrUpdateReceivedPasswords(syncResponse.passwordsAndFolderChildren)
                return when (sendRequestPasswords(syncResponse.requestingPasswordRecordIds)) {
                    null -> Result.Error
                    else -> Result.Success
                }
            }
            is SyncResponse.SimpleRespondSyncResponse -> {
                addOrUpdateReceivedPasswords(syncResponse.passwordsAndFolderChildren)
                sendSuccessSyncResult()
            }
            is SyncResponse.SimpleRequestingSyncResponse -> {
                return when (sendRequestPasswords(syncResponse.requestingPasswordRecordIds)) {
                    null -> Result.Error
                    else -> Result.Success
                }
            }
            is SyncResponse.SuccessResponse -> {
                return Result.Success
            }
            else -> {
                return Result.Error
            }
        }

        return Result.Success
    }

    private fun generateAsymmetricKeys(): AsymmetricKeyPair? {
        stateMachine.transition(GenerateAsymmetricKeys)
        if (stateMachine.state != State.AsymmetricKeysGeneration) {
            return null
        }

        asymmetricKeyPair = AsymmetricKeyPair.generateNew()
        return asymmetricKeyPair
    }

    private suspend fun sendAsymmetricKey(): EncryptedSymmetricKey? {
        stateMachine.transition(SendAsymmetricKey)
        if (stateMachine.state != State.SendingAsymmetricKey) {
            return null
        }

        return when (val publicKey = asymmetricKeyPair?.publicKey) {
            null -> {
                stateMachine.transition(ErrorSyncResult)
                null
            }
            else -> {
                sendPasswordRepository.sendPublicAsymmetricKey(publicKey.encoded())
            }
        }
    }

    private fun decryptSymmetricKey(encodedSymmetricKey: EncryptedSymmetricKey): SymmetricKey? {
        stateMachine.transition(DecryptSymmetricKey)
        if (stateMachine.state != State.DecryptionSymmetricKey) {
            return null
        }
        if (asymmetricKeyPair == null) return null

        val decryptedSymmetricKey =
            encodedSymmetricKey.decrypted(asymmetricDecryptor, asymmetricKeyPair!!.privateKey)
        symmetricKey = decryptedSymmetricKey
        return symmetricKey
    }

    private suspend fun sendInitialSyncRequest(): SyncResponse? {
        stateMachine.transition(SendInitialSyncRequest)
        if (stateMachine.state != State.SendingInitialSyncRequest) {
            return null
        }

        val syncResponse = sendEncryptedSyncPasswordRecordsUsecase(symmetricKey!!)
        return syncResponse
    }

    private suspend fun addOrUpdateReceivedPasswords(passwordsAndFolderChildren: JsonObject) {
        stateMachine.transition(AddOrUpdateReceivedPasswords)
        if (stateMachine.state != State.UpdatingReceivedPasswords) {
            return
        }

        syncPasswordRepository.addOrUpdatePasswordsAndFolderChildren(passwordsAndFolderChildren)
    }

    private suspend fun sendRequestPasswords(requestingPasswordRecordIds: List<String>): SyncResponse? {
        stateMachine.transition(SendRequestPasswords)
        if (stateMachine.state != State.SendingRequestPasswords) {
            return null
        }
        symmetricKey ?: return null

        val syncResponse =
            sendEncryptedRequestPasswordsUsecase(requestingPasswordRecordIds, symmetricKey!!)
        return syncResponse
    }

    private suspend fun sendSuccessSyncResult() {
        stateMachine.transition(SendSuccessSyncResult)
        if (stateMachine.state != State.SendingSuccessSyncResult) {
            return
        }

        sendPasswordRepository.sendSuccessSyncResult()
    }

}

class ClientSyncStateMachine(
    setSyncStatus: SetSyncStatusUseCase
) : IStateMachine<State, Event, Nothing> {

    override val state: State
        get() = stateMachine.state

    override fun transition(event: Event): StateMachine.Transition<State, Event, Nothing> =
        stateMachine.transition(event)

    sealed interface State {
        object Initial : State
        object AsymmetricKeysGeneration : State
        object SendingAsymmetricKey : State
        object DecryptionSymmetricKey : State
        object SendingInitialSyncRequest : State

        object UpdatingReceivedPasswords : State
        object SendingRequestPasswords : State
        object SendingSuccessSyncResult : State

        object SuccessSyncResult : State
        object ErrorSyncResponse : State
    }

    sealed interface Event {
        object GenerateAsymmetricKeys : Event
        object SendAsymmetricKey : Event
        object DecryptSymmetricKey : Event
        object SendInitialSyncRequest : Event

        object AddOrUpdateReceivedPasswords : Event
        object SendRequestPasswords : Event
        object SendSuccessSyncResult : Event

        object SuccessSyncResult : Event
        object ErrorSyncResult : Event
    }

    private val stateMachine = StateMachine.create<State, Event, Nothing> {
        initialState(State.Initial)

        state<State.Initial> {
            on<GenerateAsymmetricKeys> {
                transitionTo(State.AsymmetricKeysGeneration)
            }
        }

        state<State.AsymmetricKeysGeneration> {
            on<SendAsymmetricKey> {
                transitionTo(State.SendingAsymmetricKey)
            }
        }

        state<State.SendingAsymmetricKey> {
            on<DecryptSymmetricKey> {
                transitionTo(State.DecryptionSymmetricKey)
            }
            on<ErrorSyncResult> {
                transitionTo(State.ErrorSyncResponse)
            }
        }

        state<State.DecryptionSymmetricKey> {
            on<SendInitialSyncRequest> {
                transitionTo(State.SendingInitialSyncRequest)
            }
            on<ErrorSyncResult> {
                transitionTo(State.ErrorSyncResponse)
            }
        }

        state<State.SendingInitialSyncRequest> {
            on<AddOrUpdateReceivedPasswords> {
                transitionTo(State.UpdatingReceivedPasswords)
            }
            on<SendRequestPasswords> {
                transitionTo(State.SendingRequestPasswords)
            }
            on<SuccessSyncResult> {
                transitionTo(State.SuccessSyncResult)
            }
            on<ErrorSyncResult> {
                transitionTo(State.ErrorSyncResponse)
            }
        }

        state<State.UpdatingReceivedPasswords> {
            on<SendRequestPasswords> {
                transitionTo(State.SendingRequestPasswords)
            }
            on<SendSuccessSyncResult> {
                transitionTo(State.SendingSuccessSyncResult)
            }
        }

        state<State.SendingRequestPasswords> {
            on<SuccessSyncResult> {
                dontTransition()
            }
        }

        state<State.SuccessSyncResult> {
            //TODO
        }

        onTransition {
            val validTransition = when (it) {
                is StateMachine.Transition.Invalid -> error("invalid state")
                is StateMachine.Transition.Valid -> it
            }
            Napier.e("ClientSyncUseCase onTransition = $validTransition")

            val syncStatus = validTransition.toState.toSyncStatus()
            setSyncStatus(syncStatus)
        }
    }
}

private fun State.toSyncStatus(): SyncStatus =
    when (this) {
        State.Initial -> SyncStatus.Initial
        State.AsymmetricKeysGeneration -> SyncStatus.Initial
        State.SendingAsymmetricKey -> SyncStatus.SendingPasswords
        State.DecryptionSymmetricKey -> SyncStatus.SendingPasswords
        State.SendingInitialSyncRequest -> SyncStatus.SendingPasswords
        State.UpdatingReceivedPasswords -> SyncStatus.SavingReceivedPasswords
        State.SendingRequestPasswords -> SyncStatus.SendingPasswords
        State.SendingSuccessSyncResult -> SyncStatus.SuccessSync
        State.SuccessSyncResult -> SyncStatus.SuccessSync
        State.ErrorSyncResponse -> SyncStatus.ErrorSync
    }