package ru.dimagor555.passwordgeneration.domain

data class PasswordGenerationParams(
    val length: PasswordLength,
    val charGroups: List<PasswordCharGroup>
)