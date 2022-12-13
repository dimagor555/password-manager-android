package ru.dimagor555.password.usecase.password

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.domain.password.*
import ru.dimagor555.password.domain.password.field.EncryptedPasswordField
import ru.dimagor555.password.domain.password.field.PASSWORD_FIELD_KEY
import ru.dimagor555.password.repository.FolderChildrenRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.validation.core.TextValidationError

class UpdatePasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val folderChildrenRepository: FolderChildrenRepository,
    private val encryptor: Encryptor,
) {

    data class Params(
        val id: String,
        val fromId: String,
        val toId: String?,
        val fields: PasswordFields,
    )

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
                    folderChildrenRepository.replaceChildLocation(id, fromId, toId)
                }
                Result.Success
            }
        }
    }

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
