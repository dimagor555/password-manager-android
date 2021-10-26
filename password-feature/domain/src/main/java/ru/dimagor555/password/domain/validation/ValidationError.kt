package ru.dimagor555.password.domain.validation

sealed interface ValidationError {
    object IsBlank : ValidationError

    data class IsTooLong(val maxLength: Int) : ValidationError
}