package ru.dimagor555.masterpassword.usecase.password

import ru.dimagor555.password.validation.core.*

internal class ValidatePasswordUseCase {

    operator fun invoke(password: String) = Result(
        textValidationErrors = validateText(password),
        passwordValidationErrors = validatePassword(password)
    )

    private fun validateText(password: String): Set<TextValidationError> =
        TextValidationUtil.validate(password, PASSWORD_LENGTH_SPEC).toSet()

    private fun validatePassword(password: String): Set<PasswordValidationError> =
        PasswordValidationUtil.validate(password)

    data class Result(
        val textValidationErrors: Set<TextValidationError>,
        val passwordValidationErrors: Set<PasswordValidationError>
    )

    companion object {
        private val PASSWORD_LENGTH_SPEC = LengthSpec(minLength = 8, maxLength = 200)
    }
}