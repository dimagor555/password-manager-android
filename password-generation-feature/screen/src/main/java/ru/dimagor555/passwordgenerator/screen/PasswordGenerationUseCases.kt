package ru.dimagor555.passwordgenerator.screen

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.passwordgeneration.usecase.GeneratePasswordUseCase
import javax.inject.Inject

@ViewModelScoped
internal class PasswordGenerationUseCases @Inject constructor() {
    val generatePassword = GeneratePasswordUseCase()
}