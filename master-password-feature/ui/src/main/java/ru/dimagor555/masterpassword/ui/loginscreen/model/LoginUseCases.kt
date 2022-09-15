package ru.dimagor555.masterpassword.ui.loginscreen.model

import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import ru.dimagor555.masterpassword.usecase.LoginByPasswordUseCase

internal class LoginUseCases(
    masterPasswordRepository: MasterPasswordRepository
) {
    val loginByPassword = LoginByPasswordUseCase(masterPasswordRepository)
}