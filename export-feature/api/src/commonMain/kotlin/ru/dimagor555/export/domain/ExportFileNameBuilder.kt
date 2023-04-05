package ru.dimagor555.export.domain

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class ExportFileNameBuilder(
    val prefix: String,
    val instant: Instant? = null,
) {

    init {
        require(prefix.isNotEmpty()) { "prefix must not be empty" }
    }

    fun build(): String = buildString {
        append(prefix)
        instant?.let {
            append(SEPARATOR)
            append(it.format())
        }
        append(".")
        append(EXTENSION)
    }

    private fun Instant.format(): String {
        val dateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
        val components = dateTime.run {
            listOf(year, monthNumber, dayOfMonth, hour, minute, second)
        }
        return components.joinToString(separator = SEPARATOR)
    }

    companion object {
        private const val EXTENSION = "pass"
        private const val SEPARATOR = "-"
    }
}