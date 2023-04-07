package ru.dimagor555.masterpassword.ui.loginscreen.store

import ru.dimagor555.masterpassword.usecase.password.LoginByPasswordUseCase

internal class LoginUseCases(
    val loginByPassword: LoginByPasswordUseCase,
)