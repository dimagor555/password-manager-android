package ru.dimagor555.passwordgeneration.domain

enum class PasswordLength(val value: Int) {
    SHORT(16),
    MEDIUM(32),
    LONG(64),
    MAXIMUM(100)
}