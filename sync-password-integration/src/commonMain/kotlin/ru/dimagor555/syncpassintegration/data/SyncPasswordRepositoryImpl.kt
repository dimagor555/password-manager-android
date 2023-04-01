package ru.dimagor555.syncpassintegration.data

import io.github.aakira.napier.Napier
import kotlinx.serialization.json.*
import org.koin.core.component.KoinComponent
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.AddOrUpdatePasswordsAndFolderChildrenUsecase
import ru.dimagor555.password.usecase.passwordsandfolderchildren.AddOrUpdatePasswordsAndFolderChildrenUsecase.Params
import ru.dimagor555.password.usecase.passwordsandfolderchildren.AddOrUpdatePasswordsAndFolderChildrenUsecase.StatisticsResult
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.syncresult.SyncResult
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncresult.UpdateSyncResultUseCase
import ru.dimagor555.syncpassintegration.domain.toSyncPasswordRecords
import ru.dimagor555.syncpassintegration.usecase.FilterPasswordAndFolderChildrenUseCase

class SyncPasswordRepositoryImpl(
    private val passwordRepository: PasswordRepository,
    private val addOrUpdatePasswordsAndFolderChildren: AddOrUpdatePasswordsAndFolderChildrenUsecase,
    private val filterPasswordAndFolderChildren: FilterPasswordAndFolderChildrenUseCase,
    private val updateSyncResult: UpdateSyncResultUseCase,
) : SyncPasswordRepository, KoinComponent {

    override suspend fun getAllSyncRecords(): List<SyncPasswordRecord> {
        val passwords = passwordRepository.getAll()
        return passwords.toSyncPasswordRecords()
    }

    override suspend fun getPasswordsAndFolderChildren(passwordsIds: List<String>): JsonObject? {
        val filteredPasswordsAndFolderChildren =
            filterPasswordAndFolderChildren(passwordsIds) ?: return null
        return Json
            .encodeToJsonElement(filteredPasswordsAndFolderChildren)
            .jsonObject
    }

    override suspend fun addOrUpdatePasswordsAndFolderChildren(passwordsAndFolderChildren: JsonObject) {
        Napier.e("SyncPasswordRepositoryImpl addOrUpdatePasswordsAndFolderChildren value = $passwordsAndFolderChildren")
        val passwordsAndFolderChildrenModel =
            Json.decodeFromJsonElement<PasswordsAndFolderChildren>(passwordsAndFolderChildren)
        Napier.e("SyncPasswordRepositoryImpl addOrUpdatePasswordsAndFolderChildren passwordsAndFolderChildren = $passwordsAndFolderChildrenModel")

        val statisticsResult = addOrUpdatePasswordsAndFolderChildren(
            Params(passwordsAndFolderChildrenModel)
        )
        updateSyncResult(statisticsResult.toSyncResult())

        Napier.e("SyncPasswordRepositoryImpl addOrUpdatePasswordsAndFolderChildren final")
    }

    private fun StatisticsResult.toSyncResult() = SyncResult(
        addedPasswordsCount = addedPasswordsCount,
        updatedPasswordsCount = updatedPasswordsCount,
        archivedPasswordsCount = archivedPasswordsCount,
    )
}
