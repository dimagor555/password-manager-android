package ru.dimagor555.password.domain

data class EncryptedPassword(
    val title: String,
    val login: String,
    val password: String
)