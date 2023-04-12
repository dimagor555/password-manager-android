package ru.dimagor555.password.usecase.field.repository

interface ClipboardRepository {
    suspend fun setText(text: String)
}
