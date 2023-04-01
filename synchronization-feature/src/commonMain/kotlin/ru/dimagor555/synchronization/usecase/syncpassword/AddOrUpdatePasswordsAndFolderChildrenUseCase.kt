package ru.dimagor555.synchronization.usecase.syncpassword

import kotlinx.serialization.json.JsonObject
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository

class AddOrUpdatePasswordsAndFolderChildrenUseCase(
    private val syncPasswordRepository: SyncPasswordRepository,
) {
    suspend operator fun invoke(passwordsAndFolderChildren: JsonObject) =
        syncPasswordRepository.addOrUpdatePasswordsAndFolderChildren(passwordsAndFolderChildren)
}