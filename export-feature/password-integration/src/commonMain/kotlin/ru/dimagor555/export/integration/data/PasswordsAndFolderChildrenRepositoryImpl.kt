package ru.dimagor555.export.integration.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.*
import ru.dimagor555.export.domain.Export
import ru.dimagor555.password.usecase.passwordsandfolderchildren.AddOrUpdatePasswordsAndFolderChildrenUsecase
import ru.dimagor555.password.usecase.passwordsandfolderchildren.AddPasswordsAndFolderChildrenUsecase
import ru.dimagor555.password.usecase.passwordsandfolderchildren.GetPasswordsAndFolderChildrenUsecase
import ru.dimagor555.password.usecase.passwordsandfolderchildren.RemovePasswordsAndFolderChildrenUsecase
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren
import ru.dimagor555.export.usecase.repository.PasswordsAndFolderChildrenRepository as ExportPasswordRepository

class PasswordsAndFolderChildrenRepositoryImpl(
    private val getPasswordsAndFolderChildren: GetPasswordsAndFolderChildrenUsecase,
    private val addOrUpdatePasswordsAndFolderChildren: AddOrUpdatePasswordsAndFolderChildrenUsecase,
    private val removePasswordsAndFolderChildren: RemovePasswordsAndFolderChildrenUsecase,
    private val addPasswordsAndFolderChildren: AddPasswordsAndFolderChildrenUsecase,
) : ExportPasswordRepository {

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    override suspend fun getOrNull(): JsonObject? = withContext(Dispatchers.Default) {
        getPasswordsAndFolderChildren()
            ?.let { json.encodeToJsonElement(it).jsonObject }
    }

    override suspend fun import(export: Export): Unit = withContext(Dispatchers.Default) {
        addOrUpdatePasswordsAndFolderChildren(
            AddOrUpdatePasswordsAndFolderChildrenUsecase.Params(
                passwordsAndFolderChildren = export.toPasswordsAndFolderChildren(),
                isForceUpdate = true,
            ),
        )
    }

    private fun Export.toPasswordsAndFolderChildren(): PasswordsAndFolderChildren =
        json.decodeFromJsonElement(passwordsAndFolderChildren)

    override suspend fun importWithClearing(export: Export) =
        withContext(Dispatchers.Default + NonCancellable) {
            removePasswordsAndFolderChildren()
            addPasswordsAndFolderChildren(export.toPasswordsAndFolderChildren())
        }
}

