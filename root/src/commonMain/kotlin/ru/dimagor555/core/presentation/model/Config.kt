package ru.dimagor555.core.presentation.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

internal sealed class Config : Parcelable {
    @Parcelize
    object Welcome : Config()

    @Parcelize
    object Login : Config()

    @Parcelize
    object EditMasterPassword : Config()

    @Parcelize
    object PasswordList : Config()

    @Parcelize
    data class EditPassword(val passwordId: Int) : Config()

    @Parcelize
    data class PasswordDetails(val passwordId: Int) : Config()

    @Parcelize
    object CreatePassword : Config()

    @Parcelize
    object PasswordGeneration : Config()

}