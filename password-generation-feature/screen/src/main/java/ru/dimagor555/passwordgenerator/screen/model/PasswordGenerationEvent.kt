package ru.dimagor555.passwordgenerator.screen.model

import ru.dimagor555.passwordgeneration.domain.PasswordCharGroup
import ru.dimagor555.passwordgeneration.domain.PasswordLength

internal sealed class PasswordGenerationEvent {
    object GeneratePassword : PasswordGenerationEvent()

    data class OnLengthChanged(val length: PasswordLength) : PasswordGenerationEvent()
    data class OnToggleCharGroup(val charGroup: PasswordCharGroup) : PasswordGenerationEvent()
}