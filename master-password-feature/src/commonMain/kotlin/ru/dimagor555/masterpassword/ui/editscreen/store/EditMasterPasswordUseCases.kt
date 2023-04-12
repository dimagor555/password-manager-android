package ru.dimagor555.masterpassword.ui.editscreen.store

import ru.dimagor555.masterpassword.usecase.biometric.IsBiometricAvailableUsecase
import ru.dimagor555.masterpassword.usecase.password.SetMasterPasswordUseCase
import ru.dimagor555.masterpassword.usecase.password.ValidatePasswordUseCase

internal class EditMasterPasswordUseCases(
    val validatePassword: ValidatePasswordUseCase,
    val setMasterPassword: SetMasterPasswordUseCase,
    val isBiometricAvailable: IsBiometricAvailableUsecase,
)