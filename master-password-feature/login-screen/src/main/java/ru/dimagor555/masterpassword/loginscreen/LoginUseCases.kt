package ru.dimagor555.masterpassword.loginscreen

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import ru.dimagor555.masterpassword.repository.BiometryRepository
import ru.dimagor555.masterpassword.usecase.IsBiometricLoginAvailableUseCase
import ru.dimagor555.masterpassword.usecase.LoginByPasswordUseCase
import javax.inject.Inject

@ViewModelScoped
internal class LoginUseCases @Inject constructor(
    masterPasswordRepository: MasterPasswordRepository,
    biometryRepository: BiometryRepository
) {
    val loginByPassword = LoginByPasswordUseCase(masterPasswordRepository)

    val isBiometricLoginAvailable = IsBiometricLoginAvailableUseCase(biometryRepository)
}