package ru.dimagor555.password.listscreen.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.Password

internal data class PasswordViewState(
    val id: Int,
    val title: String,
    val login: String,
    val isFavourite: Boolean
)

internal suspend fun List<Password>.toPasswordViewStates() = withContext(Dispatchers.Default) {
    map { it.toPasswordViewState() }
}

private fun Password.toPasswordViewState() = PasswordViewState(
    id = id!!,
    title = title,
    login = login,
    isFavourite = metadata.isFavourite
)
