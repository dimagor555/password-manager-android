package ru.dimagor555.passwordgeneration.domain

internal class PasswordGenerator(
    private val params: PasswordGenerationParams
) {
    private val availableChars = createAvailableChars()

    private fun createAvailableChars() =
        params.charGroups
            .map { it.chars }
            .reduce { acc, chars -> acc + chars }

    fun generate() = buildString {
        repeat(params.length.value) {
            append(availableChars.random())
        }
    }
}