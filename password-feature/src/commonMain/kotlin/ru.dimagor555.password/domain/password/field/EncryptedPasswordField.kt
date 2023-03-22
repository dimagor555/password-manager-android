package ru.dimagor555.password.domain.password.field

import kotlinx.serialization.Serializable
import ru.dimagor555.password.validation.core.LengthSpec
import ru.dimagor555.password.validation.core.TextValidationError
import ru.dimagor555.password.validation.core.TextValidationUtil

@Serializable
data class EncryptedPasswordField(
    override val key: String,
    override val text: String = "",
) : Field

class EncryptedPasswordFieldValidator : FieldValidator<EncryptedPasswordField> {

    override fun validate(field: EncryptedPasswordField): List<TextValidationError> = TextValidationUtil.validate(
        text = field.text,
        lengthSpec = LengthSpec(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH)
    )

    companion object {
        private const val MIN_PASSWORD_LENGTH = 4
        private const val MAX_PASSWORD_LENGTH = 100
    }
}
