package ru.dimagor555.masterpassword.editscreen.model

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import ru.dimagor555.masterpassword.usecase.SetMasterPasswordUseCase
import ru.dimagor555.masterpassword.usecase.ValidatePasswordUseCase
import javax.inject.Inject

@ViewModelScoped
internal class EditMasterPasswordUseCases @Inject constructor(
    masterPasswordRepository: MasterPasswordRepository
) {
    val validatePassword = ValidatePasswordUseCase()

    val setMasterPassword = SetMasterPasswordUseCase(masterPasswordRepository)
}