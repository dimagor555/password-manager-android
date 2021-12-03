package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.validation.validateLogin
import ru.dimagor555.password.domain.validation.validatePasswordText
import ru.dimagor555.password.domain.validation.validateTitle

class ValidateTitleUseCase {
    suspend operator fun invoke(title: String) = withContext(Dispatchers.Default) {
        validateTitle(title).firstOrNull()
    }
}

class ValidateLoginUseCase {
    suspend operator fun invoke(login: String) = withContext(Dispatchers.Default) {
        validateLogin(login).firstOrNull()
    }
}

class ValidatePasswordTextUseCase {
    suspend operator fun invoke(passwordText: String) = withContext(Dispatchers.Default) {
        validatePasswordText(passwordText).firstOrNull()
    }
}