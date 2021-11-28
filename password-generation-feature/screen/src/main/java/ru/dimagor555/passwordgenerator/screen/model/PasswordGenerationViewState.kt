package ru.dimagor555.passwordgenerator.screen.model

import ru.dimagor555.passwordgeneration.domain.PasswordCharGroup
import ru.dimagor555.passwordgeneration.domain.PasswordLength

internal data class PasswordGenerationViewState(
    val generatedPassword: String = "",
    val length: PasswordLength = PasswordLength.MEDIUM,
    val selectedCharGroups: List<PasswordCharGroup> = PasswordCharGroup.values().toList(),
    val isGenerateButtonEnabled: Boolean = true
)