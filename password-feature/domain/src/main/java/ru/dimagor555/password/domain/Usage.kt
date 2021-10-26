package ru.dimagor555.password.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Usage(
    val id: Int? = null,
    val datetime: Instant = Clock.System.now()
)