package ru.dimagor555.password.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Password(
    val id: Int? = null,
    val title: String,
    val login: String,
    val encryptedPassword: String,
    val isFavourite: Boolean = false,
    val creationDateTime: Instant = Clock.System.now(),
    val editingDateTime: Instant = Clock.System.now(),
    val usages: List<Usage> = emptyList()
)
