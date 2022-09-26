package ru.dimagor555.masterpassword.ui.editscreen.model

import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import ru.dimagor555.masterpassword.usecase.SetMasterPasswordUseCase
import ru.dimagor555.masterpassword.usecase.ValidatePasswordUseCase

internal class EditMasterPasswordUseCases(
    masterPasswordRepository: MasterPasswordRepository
) {
    val validatePassword = ValidatePasswordUseCase()

    val setMasterPassword = SetMasterPasswordUseCase(masterPasswordRepository)
}