package ru.dimagor555.syncpassintegration.domain

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> parseToString(value: T): String =
    Json.encodeToString(value)

inline fun <reified T> parseToValue(value: String): T =
    Json.decodeFromString(value)
