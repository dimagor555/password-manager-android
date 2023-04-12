package ru.dimagor555.password.validation.core

object PasswordValidationUtil {

    fun validate(password: String): Set<PasswordValidationError> =
        validateSymbols(password)

    private fun validateSymbols(password: String): Set<PasswordValidationError> =
        errorsByRegexes
            .filterValues { regex -> regex !in password }
            .keys

    private val errorsByRegexes = mapOf(
        PasswordValidationError.NotContainDigits to "\\d".toRegex(),
        PasswordValidationError.NotContainLowercaseLetters to "[a-zа-яё]".toRegex(),
        PasswordValidationError.NotContainUppercaseLetters to "[A-ZА-ЯЁ]".toRegex(),
        PasswordValidationError.NotContainSpecialSymbols to "[^a-zA-Zа-яА-ЯёЁ0-9]".toRegex(),
    )
}