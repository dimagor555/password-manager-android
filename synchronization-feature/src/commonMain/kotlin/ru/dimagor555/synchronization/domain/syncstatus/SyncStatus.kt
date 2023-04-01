package ru.dimagor555.synchronization.domain.syncstatus

sealed interface SyncStatus {

    object Initial : SyncStatus

    object SendingPasswords : SyncStatus

    object ReceivedPasswordsAnalysis : SyncStatus

    object SavingReceivedPasswords : SyncStatus

    object SuccessSync : SyncStatus

    object ErrorSync : SyncStatus
}