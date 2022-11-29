package ru.dimagor555.password.usecase.password

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.domain.password.field.EncryptedPasswordField
import ru.dimagor555.password.domain.password.field.PASSWORD_FIELD_KEY
import ru.dimagor555.password.domain.password.updateFieldByKey
import ru.dimagor555.password.domain.password.validate
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.validation.core.TextValidationError

class CreatePasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val encryptor: Encryptor
) {

    data class Params(
        val parentId: String,
        val fields: PasswordFields,
    )

    suspend operator fun invoke(params: Params): List<TextValidationError> =
        withContext(Dispatchers.Default) {
            validate(params.fields).ifEmpty {
                val password = createPassword(params)
                passwordRepository.add(password)
                emptyList()
            }
        }

    private fun validate(fields: PasswordFields): List<TextValidationError> = fields.validate()

    private fun createPassword(params: Params) = Password(
        parentId = params.parentId,
        fields = encryptPassword(params.fields),
    )

    private fun encryptPassword(fields: PasswordFields): PasswordFields =
        fields.updateFieldByKey<EncryptedPasswordField>(
            key = PASSWORD_FIELD_KEY,
        ) {
            EncryptedPasswordField(PASSWORD_FIELD_KEY, encryptor.encrypt(it.text))
        }
}
