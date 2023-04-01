package ru.dimagor555.synchronization.domain.syncresult

data class SyncResult(
    val addedPasswordsCount: Int = 0,
    val updatedPasswordsCount: Int = 0,
    val archivedPasswordsCount: Int = 0,
)
