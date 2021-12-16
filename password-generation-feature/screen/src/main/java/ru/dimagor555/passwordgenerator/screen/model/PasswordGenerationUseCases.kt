package ru.dimagor555.passwordgenerator.screen.model

import ru.dimagor555.passwordgeneration.usecase.GeneratePasswordUseCase
import ru.dimagor555.passwordgeneration.usecase.ToggleCharGroupUseCase

internal class PasswordGenerationUseCases {
    val generatePassword = GeneratePasswordUseCase()

    val toggleCharGroup = ToggleCharGroupUseCase()
}