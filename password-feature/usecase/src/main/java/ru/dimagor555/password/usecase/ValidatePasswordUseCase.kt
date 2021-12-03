package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.validation.validateLogin
import ru.dimagor555.password.domain.validation.validatePasswordText
import ru.dimagor555.password.domain.validation.validateTitle
import ru.dimagor555.password.validation.TextValidationError

class ValidatePasswordUseCase {
    suspend operator fun invoke(params: Params) = withContext(Dispatchers.Default) {
        Result(
            titleError = validateTitle(params.title).firstOrNull(),
            loginError = validateLogin(params.login).firstOrNull(),
            passwordError = validatePasswordText(params.password).firstOrNull()
        )
    }

    data class Params(
        val title: String,
        val login: String,
        val password: String
    )

    data class Result(
        val titleError: TextValidationError?,
        val loginError: TextValidationError?,
        val passwordError: TextValidationError?
    )
}