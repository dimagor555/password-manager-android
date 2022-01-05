package ru.dimagor555.password.commonedit.model

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.password.usecase.ValidateLoginUseCase
import ru.dimagor555.password.usecase.ValidatePasswordTextUseCase
import ru.dimagor555.password.usecase.ValidatePasswordUseCase
import ru.dimagor555.password.usecase.ValidateTitleUseCase
import javax.inject.Inject

@ViewModelScoped
class CommonEditPasswordUseCases @Inject constructor() {
    val validatePassword = ValidatePasswordUseCase()

    val validateTitle = ValidateTitleUseCase()
    val validateLogin = ValidateLoginUseCase()
    val validatePasswordText = ValidatePasswordTextUseCase()
}