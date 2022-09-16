package ru.dimagor555.password.ui.detailsscreen.model

import ru.dimagor555.password.domain.Password

internal data class PasswordState(
    val title: String = "",
    val login: String = "",
    val isFavourite: Boolean = false
)

internal fun Password.toPasswordState() = PasswordState(
    title = title,
    login = login,
    isFavourite = metadata.isFavourite
)
