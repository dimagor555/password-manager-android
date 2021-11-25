package ru.dimagor555.password.domain.validation

import ru.dimagor555.password.validation.LengthSpec
import ru.dimagor555.password.validation.TextValidationUtil

class PasswordValidation(
    val title: String,
    val login: String,
    val password: String
) {
    fun validate() {
        val titleErrors = validateTitle(title)
        val loginErrors = validateLogin(login)
        val passwordErrors = validatePasswordText(password)
        val allErrors = (titleErrors + loginErrors + passwordErrors)
        if (allErrors.isNotEmpty())
            throw PasswordValidationException(titleErrors, loginErrors, passwordErrors)
    }
}

fun validateTitle(title: String) = TextValidationUtil.validate(
    text = title,
    lengthSpec = LengthSpec(maxLength = MAX_TITLE_LENGTH)
)

fun validateLogin(login: String) = TextValidationUtil.validate(
    text = login,
    lengthSpec = LengthSpec(maxLength = MAX_LOGIN_LENGTH)
)

fun validatePasswordText(passwordText: String) = TextValidationUtil.validate(
    text = passwordText,
    lengthSpec = LengthSpec(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH)
)

private const val MAX_TITLE_LENGTH = 100
private const val MAX_LOGIN_LENGTH = 100

private const val MIN_PASSWORD_LENGTH = 4
private const val MAX_PASSWORD_LENGTH = 100