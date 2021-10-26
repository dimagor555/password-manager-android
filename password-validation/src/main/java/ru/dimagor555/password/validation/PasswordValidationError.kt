package ru.dimagor555.password.validation

sealed class PasswordValidationError {
    object IsBlank : PasswordValidationError()

    data class IsTooShort(val minLength: Int) : PasswordValidationError()

    data class IsTooLong(val maxLength: Int) : PasswordValidationError()

    object NotContainDigits : PasswordValidationError()

    object NotContainLowercaseLetters : PasswordValidationError()

    object NotContainUppercaseLetters : PasswordValidationError()

    object NotContainSpecialSymbols : PasswordValidationError()
}
