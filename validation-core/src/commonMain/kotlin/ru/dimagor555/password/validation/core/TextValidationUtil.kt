package ru.dimagor555.password.validation.core

object TextValidationUtil {

    fun validate(text: String, lengthSpec: LengthSpec): List<TextValidationError> {
        val validationErrors = mutableListOf<TextValidationError>()
        validationErrors += validateIsNotBlank(text)
        validationErrors += validateLength(text, lengthSpec)
        return validationErrors.toList()
    }

    private fun validateIsNotBlank(text: String): List<TextValidationError> {
        return if (text.isBlank()) {
            listOf(TextValidationError.IsBlank)
        } else {
            emptyList()
        }
    }

    private fun validateLength(
        text: String,
        lengthSpec: LengthSpec
    ): List<TextValidationError> {
        val errors = mutableListOf<TextValidationError>()
        if (text.length < lengthSpec.minLength) {
            errors += TextValidationError.IsTooShort(lengthSpec.minLength)
        }
        if (text.length > lengthSpec.maxLength) {
            errors += TextValidationError.IsTooLong(lengthSpec.maxLength)
        }
        return errors
    }
}