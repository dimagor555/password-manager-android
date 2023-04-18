package ru.dimagor555.synchronization.usecase

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import ru.dimagor555.encryption.asymmetric.domain.AsymmetricEncryptor
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPublicKey
import ru.dimagor555.encryption.symmetric.data.key.generateNew
import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import ru.dimagor555.synchronization.domain.key.EncodedAsymmetricPublicKey
import ru.dimagor555.synchronization.domain.key.EncryptedSymmetricKey
import ru.dimagor555.synchronization.domain.key.decoded
import ru.dimagor555.synchronization.domain.key.encrypted
import ru.dimagor555.synchronization.domain.request.EncryptedInitialSyncRequest
import ru.dimagor555.synchronization.domain.response.EncryptedSyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.response.decrypted
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.ServerSyncStateMachine.Event
import ru.dimagor555.synchronization.usecase.ServerSyncStateMachine.State
import ru.dimagor555.synchronization.usecase.rest.ReceiveEncryptedInitialSyncRequestUsecase
import ru.dimagor555.synchronization.usecase.syncpassword.AddOrUpdatePasswordsAndFolderChildrenUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

internal class ServerSyncUseCase(
    setSyncStatus: SetSyncStatusUseCase,
    private val addOrUpdatePasswordsAndFolderChildren: AddOrUpdatePasswordsAndFolderChildrenUseCase,
    private val receiveEncryptedInitialSyncRequest: ReceiveEncryptedInitialSyncRequestUsecase,
    private val decryptor: SymmetricDecryptor,
    private val encryptor: AsymmetricEncryptor,
) {

    private val stateMachine = ServerSyncStateMachine(setSyncStatus)

    private var asymmetricPublicKey: AsymmetricPublicKey? = null
    private var symmetricKey: SymmetricKey? = null

    val receiveType: Channel<ReceiveType> = Channel()
    val respondType: Channel<RespondType> = Channel()

    sealed interface ReceiveType {

        data class AsymmetricPublicKeyReceiveType(
            val asymmetricPublicKey: EncodedAsymmetricPublicKey,
        ) : ReceiveType

        data class InitialSyncRequestReceiveType(
            val encryptedInitialSyncRequest: EncryptedInitialSyncRequest,
        ) : ReceiveType

        data class SyncResponseReceiveType(
            val encryptedSyncResponse: EncryptedSyncResponse,
        ) : ReceiveType
    }

    sealed interface RespondType {

        data class SymmetricKeyRespondType(
            val encryptedSymmetricKey: EncryptedSymmetricKey,
        ) : RespondType

        data class SyncResponseRespondType(
            val syncResponse: EncryptedSyncResponse,
        ) : RespondType

        object SuccessRespondType : RespondType
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            for (receiveType in receiveType) {
                observeReceiveType(receiveType)
            }
        }
    }

    private suspend fun observeReceiveType(receiveType1: ReceiveType) {
        when (receiveType1) {
            is ReceiveType.AsymmetricPublicKeyReceiveType -> {
                receiveAsymmetricPublicKey(receiveType1.asymmetricPublicKey)
                generateSymmetricKey() ?: return
                respondSymmetricKey()
            }
            is ReceiveType.InitialSyncRequestReceiveType -> {
                val syncResponse =
                    receiveInitialSyncRequest(receiveType1.encryptedInitialSyncRequest) ?: return
                respondSyncResponse(syncResponse)
            }
            is ReceiveType.SyncResponseReceiveType -> {
                if (symmetricKey == null) return
                val decryptedSyncResponse =
                    receiveType1.encryptedSyncResponse.decrypted(decryptor, symmetricKey!!)
                when (decryptedSyncResponse) {
                    is SyncResponse.SimpleRespondSyncResponse -> {
                        receiveSyncResponse(decryptedSyncResponse.passwordsAndFolderChildren)
                        respondSuccessSyncResponse()
                        successSyncResult()
                    }
                    is SyncResponse.SuccessResponse -> {
                        successSyncResult()
                    }
                    else -> error("Illegal SyncResponse")
                }
            }
        }
    }

    private fun receiveAsymmetricPublicKey(publicKey: EncodedAsymmetricPublicKey) {
        stateMachine.transition(Event.ReceiveAsymmetricPublicKey)
        if (stateMachine.state != State.ReceivingAsymmetricPublicKey) {
            return
        }

        asymmetricPublicKey = publicKey.decoded()
    }

    private fun generateSymmetricKey(): SymmetricKey? {
        stateMachine.transition(Event.GenerateSymmetricKey)
        if (stateMachine.state != State.SymmetricKeyGeneration) {
            return null
        }

        symmetricKey = SymmetricKey.generateNew()
        return symmetricKey
    }

    private suspend fun respondSymmetricKey() {
        stateMachine.transition(Event.RespondSymmetricKey)
        if (stateMachine.state != State.RespondingSymmetricKey) {
            return
        }
        symmetricKey ?: return
        asymmetricPublicKey ?: return


        val encryptedSymmetricKey = symmetricKey!!.encrypted(encryptor, asymmetricPublicKey!!)
        respondType.send(
            RespondType.SymmetricKeyRespondType(encryptedSymmetricKey)
        )
    }

    private suspend fun receiveInitialSyncRequest(
        initialSyncRequest: EncryptedInitialSyncRequest,
    ): EncryptedSyncResponse? {
        stateMachine.transition(Event.ReceiveInitialSyncRequest)
        if (stateMachine.state != State.ReceivingInitialSyncRequest) {
            return null
        }
        if (symmetricKey == null) {
            return null
        }

        val syncResponse = receiveEncryptedInitialSyncRequest(initialSyncRequest, symmetricKey!!)
        return syncResponse
    }

    private suspend fun respondSyncResponse(encryptedSyncResponse: EncryptedSyncResponse) {
        stateMachine.transition(Event.RespondSyncResponse)
        if (stateMachine.state != State.RespondingSyncResponse) {
            return
        }

        respondType.send(RespondType.SyncResponseRespondType(encryptedSyncResponse))
    }

    private suspend fun receiveSyncResponse(passwordsAndFolderChildren: JsonObject) {
        stateMachine.transition(Event.ReceiveSimpleRespondSyncResponse)
        if (stateMachine.state != State.ReceivingSimpleRespondSyncResponse) {
            return
        }

        addOrUpdatePasswordsAndFolderChildren(passwordsAndFolderChildren)
    }

    private suspend fun respondSuccessSyncResponse() {
        stateMachine.transition(Event.RespondSuccessSyncResponse)
        if (stateMachine.state != State.RespondingSuccessSyncResponse) {
            return
        }

        respondType.send(RespondType.SuccessRespondType)
    }

    private fun successSyncResult() {
        stateMachine.transition(Event.SuccessSyncResult)
        if (stateMachine.state != State.SuccessSyncResult) {
            return
        }


    }
}

class ServerSyncStateMachine(
    setSyncStatus: SetSyncStatusUseCase
) : IStateMachine<State, Event, Nothing> {

    override val state: State
        get() = stateMachine.state

    override fun transition(event: Event): StateMachine.Transition<State, Event, Nothing> =
        stateMachine.transition(event)

    sealed interface State {
        object Initial : State
        object ReceivingAsymmetricPublicKey : State
        object SymmetricKeyGeneration : State
        object RespondingSymmetricKey : State
        object ReceivingInitialSyncRequest : State
        object RespondingSyncResponse : State
        object RespondingSuccessSyncResponse : State
        object ReceivingSimpleRespondSyncResponse : State
        object SuccessSyncResult : State
    }

    sealed interface Event {
        object GenerateSymmetricKey : Event
        object ReceiveAsymmetricPublicKey : Event
        object RespondSymmetricKey : Event
        object ReceiveInitialSyncRequest : Event
        object RespondSyncResponse : Event
        object RespondSuccessSyncResponse : Event
        object ReceiveSimpleRespondSyncResponse : Event
        object SuccessSyncResult : Event
    }

    private val stateMachine = StateMachine.create<State, Event, Nothing> {
        initialState(State.Initial)

        state<State.Initial> {
            on<Event.ReceiveAsymmetricPublicKey> {
                transitionTo(State.ReceivingAsymmetricPublicKey)
            }
        }

        state<State.ReceivingAsymmetricPublicKey> {
            on<Event.GenerateSymmetricKey> {
                transitionTo(State.SymmetricKeyGeneration)
            }
        }

        state<State.SymmetricKeyGeneration> {
            on<Event.RespondSymmetricKey> {
                transitionTo(State.RespondingSymmetricKey)
            }
        }

        state<State.RespondingSymmetricKey> {
            on<Event.ReceiveInitialSyncRequest> {
                transitionTo(State.ReceivingInitialSyncRequest)
            }
        }

        state<State.ReceivingInitialSyncRequest> {
            on<Event.RespondSyncResponse> {
                transitionTo(State.RespondingSyncResponse)
            }
            on<Event.SuccessSyncResult> {
                dontTransition()
            }
        }

        state<State.RespondingSyncResponse> {
            on<Event.ReceiveSimpleRespondSyncResponse> {
                transitionTo(State.ReceivingSimpleRespondSyncResponse)
            }
        }

        state<State.ReceivingSimpleRespondSyncResponse> {
            on<Event.RespondSuccessSyncResponse> {
                transitionTo(State.RespondingSuccessSyncResponse)
            }
            on<Event.SuccessSyncResult> {
                transitionTo(State.SuccessSyncResult)
            }
        }

        state<State.RespondingSuccessSyncResponse> {
            on<Event.SuccessSyncResult> {
                transitionTo(State.SuccessSyncResult)
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
            Napier.e("ServerSyncUseCase onTransition = $validTransition")

            val syncStatus = validTransition.toState.toSyncStatus()
            setSyncStatus(syncStatus)
        }
    }
}

private fun State.toSyncStatus(): SyncStatus =
    when (this) {
        State.Initial -> SyncStatus.Initial
        State.ReceivingAsymmetricPublicKey -> SyncStatus.Initial
        State.SymmetricKeyGeneration -> SyncStatus.Initial
        State.RespondingSymmetricKey -> SyncStatus.SendingPasswords
        State.ReceivingInitialSyncRequest -> SyncStatus.ReceivedPasswordsAnalysis
        State.RespondingSyncResponse -> SyncStatus.SendingPasswords
        State.RespondingSuccessSyncResponse ->  SyncStatus.SendingPasswords
        State.ReceivingSimpleRespondSyncResponse -> SyncStatus.SavingReceivedPasswords
        State.SuccessSyncResult -> SyncStatus.SuccessSync
    }