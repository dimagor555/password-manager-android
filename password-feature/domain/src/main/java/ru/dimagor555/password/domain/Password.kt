package ru.dimagor555.password.domain

data class Password(
    val id: Int? = null,
    val title: String,
    val login: String,
    val encryptedPassword: String,
    val metadata: PasswordMetadata = PasswordMetadata()
)
