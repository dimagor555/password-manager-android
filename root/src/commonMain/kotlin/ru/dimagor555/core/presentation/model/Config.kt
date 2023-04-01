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
    data class EditPassword(val passwordId: String) : Config()

    @Parcelize
    data class PasswordDetails(val passwordId: String, val parentId: String) : Config()

    @Parcelize
    object CreatePassword : Config()

    @Parcelize
    object PasswordGeneration : Config()

    @Parcelize
    object DevicesList : Config()

    @Parcelize
    data class Sync(val isClient: Boolean) : Config()

    @Parcelize
    data class ResultSync(val isSyncSuccess: Boolean) : Config()
}