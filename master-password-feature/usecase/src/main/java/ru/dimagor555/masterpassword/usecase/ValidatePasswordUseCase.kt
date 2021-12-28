package ru.dimagor555.masterpassword.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.validation.*

class ValidatePasswordUseCase {
    suspend operator fun invoke(password: String) = withContext(Dispatchers.Default) {
        Result(
            textValidationErrors = validateText(password),
            passwordValidationErrors = validatePassword(password)
        )
    }

    private fun validateText(password: String) =
        TextValidationUtil.validate(password, PASSWORD_LENGTH_SPEC).toSet()

    private fun validatePassword(password: String) = PasswordValidationUtil.validate(password)

    data class Result(
        val textValidationErrors: Set<TextValidationError>,
        val passwordValidationErrors: Set<PasswordValidationError>
    )

    companion object {
        private val PASSWORD_LENGTH_SPEC = LengthSpec(minLength = 8, maxLength = 200)
    }
}