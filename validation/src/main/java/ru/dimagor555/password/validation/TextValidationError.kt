package ru.dimagor555.password.validation

sealed class TextValidationError {
    object IsBlank : TextValidationError()
    data class IsTooShort(val minLength: Int) : TextValidationError()
    data class IsTooLong(val maxLength: Int) : TextValidationError()
}