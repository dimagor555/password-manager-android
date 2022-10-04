package ru.dimagor555.passwordgeneration.ui.screen

sealed interface PasswordGeneration {

    sealed interface Result {

        data class GeneratedPassword(val generatedPassword: String?) : Result
    }

}