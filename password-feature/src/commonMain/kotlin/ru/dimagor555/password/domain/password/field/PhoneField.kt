package ru.dimagor555.password.domain.password.field

import kotlinx.serialization.Serializable
import ru.dimagor555.password.validation.core.LengthSpec
import ru.dimagor555.password.validation.core.TextValidationError
import ru.dimagor555.password.validation.core.TextValidationUtil

@Serializable
data class PhoneField(
    override val key: String,
    override val text: String = "",
) : Field

class PhoneFieldValidator : FieldValidator<PhoneField> {

    override fun validate(field: PhoneField): List<TextValidationError> =
        validateLength(field.text) + validateCharacters(field.text) + validatePlus(field.text)

    private fun validateLength(text: String): List<TextValidationError> =
        TextValidationUtil.validate(
            text = text,
            lengthSpec = LengthSpec(MIN_PHONE_LENGTH, MAX_PHONE_LENGTH)
        )

    private fun validateCharacters(text: String): List<TextValidationError> {
        val isContain = text.all { PHONE_VALID_CHARACTERS.contains(it) }
        return if (isContain) listOf(TextValidationError.IsIncorrectFormat) else emptyList()
    }

    private fun validatePlus(text: String): List<TextValidationError> {
        val isContain = text.startsWith('+')
        return if (isContain) listOf(TextValidationError.IsIncorrectFormat) else emptyList()
    }

    companion object {
        private const val MIN_PHONE_LENGTH = 7
        private const val MAX_PHONE_LENGTH = 20
        private const val PHONE_VALID_CHARACTERS = "0123456789"
    }
}