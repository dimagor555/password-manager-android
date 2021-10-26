package ru.dimagor555.password.domain.validation

import ru.dimagor555.password.validation.PasswordLengthSpec
import ru.dimagor555.password.validation.PasswordValidationError
import ru.dimagor555.password.validation.PasswordValidationException
import ru.dimagor555.password.validation.PasswordValidationUtil

data class PasswordValidation(
    val title: String,
    val login: String,
    val password: String
) {
    fun validate() {
        validateTitle()
        validateLogin()
        validatePassword()
    }

    private fun validateTitle() {
        val validationErrors = findSimpleValidationErrors(
            string = title,
            maxLength = MAX_TITLE_LENGTH
        )
        if (validationErrors.isNotEmpty())
            throw ValidationException.InvalidTitleException(validationErrors)
    }

    private fun findSimpleValidationErrors(string: String, maxLength: Int): List<ValidationError> {
        val validationErrors = mutableListOf<ValidationError>()
        if (string.isBlank())
            validationErrors += ValidationError.IsBlank
        if (string.length > maxLength)
            validationErrors += ValidationError.IsTooLong(maxLength = maxLength)
        return validationErrors
    }

    private fun validateLogin() {
        val validationErrors = findSimpleValidationErrors(
            string = login,
            maxLength = MAX_LOGIN_LENGTH
        )
        if (validationErrors.isNotEmpty())
            throw ValidationException.InvalidLoginException(validationErrors)
    }

    private fun validatePassword() {
        val validationErrors = findPasswordValidationErrors()
        if (validationErrors.isNotEmpty())
            throw ValidationException.InvalidPasswordException(validationErrors)
    }

    private fun findPasswordValidationErrors(): List<PasswordValidationError> {
        try {
            PasswordValidationUtil.validate(password, createPasswordLengthSpec())
        } catch (e: PasswordValidationException) {
            return e.validationErrors
        }
        return emptyList()
    }

    private fun createPasswordLengthSpec() = PasswordLengthSpec(
        minLength = MIN_PASSWORD_LENGTH,
        maxLength = MAX_PASSWORD_LENGTH
    )

    companion object {
        private const val MAX_TITLE_LENGTH = 100
        private const val MAX_LOGIN_LENGTH = 100

        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 100
    }
}
