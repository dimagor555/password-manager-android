package ru.dimagor555.masterpassword.loginscreen.model

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import ru.dimagor555.masterpassword.usecase.LoginByPasswordUseCase
import javax.inject.Inject

@ViewModelScoped
internal class LoginUseCases @Inject constructor(
    masterPasswordRepository: MasterPasswordRepository
) {
    val loginByPassword = LoginByPasswordUseCase(masterPasswordRepository)
}