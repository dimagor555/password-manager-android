package ru.dimagor555.export.domain

import kotlinx.serialization.Serializable

@Serializable
enum class ExportVersion {
    ONE;

    companion object {
        val LATEST = ONE
    }
}