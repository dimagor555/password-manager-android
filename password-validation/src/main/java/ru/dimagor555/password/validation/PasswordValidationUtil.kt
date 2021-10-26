package ru.dimagor555.password.validation

object PasswordValidationUtil {
    fun validate(password: String, passwordLengthSpec: PasswordLengthSpec) {
        val validationErrors = mutableListOf<PasswordValidationError>()
        validationErrors += validateIsNotBlank(password)
        validationErrors += validateLength(password, passwordLengthSpec)
        validationErrors += validateSymbols(password)
        throwExceptionIfHasErrors(validationErrors)
    }

    private fun validateIsNotBlank(password: String): List<PasswordValidationError> {
        return if (password.isBlank())
            listOf(PasswordValidationError.IsBlank)
        else
            emptyList()
    }

    private fun validateLength(
        password: String,
        passwordLengthSpec: PasswordLengthSpec
    ): List<PasswordValidationError> {
        val errors = mutableListOf<PasswordValidationError>()
        if (password.length < passwordLengthSpec.minLength)
            errors += PasswordValidationError.IsTooShort(passwordLengthSpec.minLength)
        if (password.length > passwordLengthSpec.maxLength)
            errors += PasswordValidationError.IsTooLong(passwordLengthSpec.maxLength)
        return errors
    }

    private fun validateSymbols(password: String) =
        errorsByRegexes
            .filterValues { regex -> regex !in password }
            .keys

    private val errorsByRegexes = mapOf(
        PasswordValidationError.NotContainDigits to "\\d".toRegex(),
        PasswordValidationError.NotContainLowercaseLetters to "[a-zа-яё]".toRegex(),
        PasswordValidationError.NotContainUppercaseLetters to "[A-ZА-ЯЁ]".toRegex(),
        PasswordValidationError.NotContainSpecialSymbols to "[^a-zA-Zа-яА-ЯёЁ0-9]".toRegex(),
    )

    private fun throwExceptionIfHasErrors(validationErrors: List<PasswordValidationError>) {
        if (validationErrors.isNotEmpty())
            throw PasswordValidationException(validationErrors)
    }
}