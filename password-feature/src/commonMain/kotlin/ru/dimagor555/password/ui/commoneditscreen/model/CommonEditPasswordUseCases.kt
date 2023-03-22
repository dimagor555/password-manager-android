package ru.dimagor555.password.ui.commoneditscreen.model

import ru.dimagor555.password.usecase.validation.ValidateFieldUseCase
import ru.dimagor555.password.usecase.validation.ValidatePasswordUseCase

internal class CommonEditPasswordUseCases {
    val validatePassword = ValidatePasswordUseCase()
    val validateField = ValidateFieldUseCase()
}
