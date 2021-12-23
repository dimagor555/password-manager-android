package ru.dimagor555.password.detailsscreen.model

import ru.dimagor555.password.domain.Password

internal data class PasswordViewState(
    val title: String = "",
    val login: String = "",
    val isFavourite: Boolean = false
)

internal fun Password.toPasswordViewState() = PasswordViewState(
    title = title,
    login = login,
    isFavourite = metadata.isFavourite
)
