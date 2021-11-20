package ru.dimagor555.password.listscreen.model

import ru.dimagor555.password.domain.Password

internal data class PasswordViewState(
    val id: Int,
    val title: String,
    val login: String,
    val isFavourite: Boolean
)

internal fun Password.toPasswordViewState() = PasswordViewState(
    id = id!!,
    title = title,
    login = login,
    isFavourite = isFavourite
)