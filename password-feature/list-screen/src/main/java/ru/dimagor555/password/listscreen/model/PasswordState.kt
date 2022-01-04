package ru.dimagor555.password.listscreen.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.Password

internal data class PasswordState(
    val id: Int,
    val title: String,
    val login: String,
    val isFavourite: Boolean
)

internal suspend fun List<Password>.toPasswordStates() = withContext(Dispatchers.Default) {
    map { it.toPasswordState() }
}

private fun Password.toPasswordState() = PasswordState(
    id = id!!,
    title = title,
    login = login,
    isFavourite = metadata.isFavourite
)
