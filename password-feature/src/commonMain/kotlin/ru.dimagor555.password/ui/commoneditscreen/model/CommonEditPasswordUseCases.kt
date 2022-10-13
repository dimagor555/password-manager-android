package ru.dimagor555.password.ui.commoneditscreen.model

import ru.dimagor555.password.usecase.ValidateLoginUseCase
import ru.dimagor555.password.usecase.ValidatePasswordTextUseCase
import ru.dimagor555.password.usecase.ValidatePasswordUseCase
import ru.dimagor555.password.usecase.ValidateTitleUseCase

internal class CommonEditPasswordUseCases {
    val validatePassword = ValidatePasswordUseCase()

    val validateTitle = ValidateTitleUseCase()
    val validateLogin = ValidateLoginUseCase()
    val validatePasswordText = ValidatePasswordTextUseCase()
}