package ru.dimagor555.password.ui.listscreen.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.getUniqueIdentifier
import ru.dimagor555.password.domain.password.title

internal data class PasswordState(
    val passwordId: String,
    val title: String,
    val uniqueIdentifier: String,
    val isFavourite: Boolean,
)

internal suspend fun List<Password>.toPasswordStates() = withContext(Dispatchers.Default) {
    map { it.toPasswordState() }
}

private fun Password.toPasswordState() = PasswordState(
    passwordId = id!!,
    title = fields.title.text,
    uniqueIdentifier = this.getUniqueIdentifier(),
    isFavourite = metadata.isFavourite,
)
