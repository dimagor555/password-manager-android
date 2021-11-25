package ru.dimagor555.password.validation

sealed class PasswordValidationError {
    object NotContainDigits : PasswordValidationError()
    object NotContainLowercaseLetters : PasswordValidationError()
    object NotContainUppercaseLetters : PasswordValidationError()
    object NotContainSpecialSymbols : PasswordValidationError()
}