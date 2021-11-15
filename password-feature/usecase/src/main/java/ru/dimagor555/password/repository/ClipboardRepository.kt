package ru.dimagor555.password.repository

interface ClipboardRepository {
    suspend fun setText(text: String)
}