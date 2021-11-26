package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.validation.validateLogin
import ru.dimagor555.password.domain.validation.validatePasswordText
import ru.dimagor555.password.domain.validation.validateTitle

class ValidateTitleUseCase {
    operator fun invoke(title: String) = validateTitle(title).firstOrNull()
}

class ValidateLoginUseCase {
    operator fun invoke(login: String) = validateLogin(login).firstOrNull()
}

class ValidatePasswordTextUseCase {
    operator fun invoke(passwordText: String) = validatePasswordText(passwordText).firstOrNull()
}