package ru.dimagor555.password.usecase.password.single

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.password.*
import ru.dimagor555.password.domain.password.field.EncryptedPasswordField
import ru.dimagor555.password.domain.password.field.PASSWORD_FIELD_KEY
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParam
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildrenRepository
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.validation.core.TextValidationError

// TODO remove password from all folders when adding to archive
// TODO remove password from archive when added to any folder
class UpdatePasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val folderChildrenRepository: FolderChildrenRepository,
    private val encryptor: SymmetricEncryptor,
) {

    data class Params(
        val id: String,
        val fromId: String,
        val toId: String? = null,
        val fields: PasswordFields,
    )

    // TODO unclear code flow
    suspend operator fun invoke(params: Params): Result =
        withContext(Dispatchers.Default) {
            validate(params)
        }

    private suspend fun validate(params: Params): Result = with(params) {
        val errors = fields.validate()
        if (errors.isEmpty()) {
            updatePassword(params)
        } else {
            Result.ValidationError(
                fields.fields.associate {
                    Pair(it.key, findFieldError(it))
                }
            )
        }
    }

    private suspend fun updatePassword(params: Params): Result = with(params) {
        when (val oldPassword = passwordRepository.getById(id)) {
            null -> Result.Error
            else -> {
                val newPassword = oldPassword.createUpdated(params)
                passwordRepository.update(newPassword)
                if (toId != null) {
                    folderChildrenRepository.changeChildFolder(
                        ChangeFolderParam.Move(
                            childId = ChildId.PasswordId(id),
                            fromParentId = fromId,
                            toParentId = toId,
                        ),
                    )
                }
                Result.Success
            }
        }
    }

    // TODO bad naming, consider just updated()
    private fun Password.createUpdated(params: Params) = copy(
        fields = updatePassword(params.fields),
        metadata = metadata.copy(editingDateTime = Clock.System.now())
    )

    private fun updatePassword(fields: PasswordFields): PasswordFields =
        fields.updateFieldByKey<EncryptedPasswordField>(
            key = PASSWORD_FIELD_KEY,
        ) {
            EncryptedPasswordField(PASSWORD_FIELD_KEY, encryptor.encrypt(it.text))
        }

    sealed interface Result {

        object Success : Result

        object Error : Result

        data class ValidationError(
            val errorsByFieldTypes: Map<String, TextValidationError?>
        ) : Result
    }
}
