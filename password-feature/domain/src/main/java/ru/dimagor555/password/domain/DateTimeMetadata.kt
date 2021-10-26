package ru.dimagor555.password.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class DateTimeMetadata(
    val creationDateTime: Instant = Clock.System.now(),
    val editingDateTime: Instant = Clock.System.now()
) {
    fun editedNow() = this.copy(editingDateTime = Clock.System.now())
}