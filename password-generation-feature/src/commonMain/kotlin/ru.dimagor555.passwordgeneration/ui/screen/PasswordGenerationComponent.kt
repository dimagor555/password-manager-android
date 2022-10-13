package ru.dimagor555.passwordgeneration.ui.screen

sealed interface PasswordGenerationComponent {

    sealed interface Result {

        data class GeneratedPassword(val generatedPassword: String?) : Result
    }

}