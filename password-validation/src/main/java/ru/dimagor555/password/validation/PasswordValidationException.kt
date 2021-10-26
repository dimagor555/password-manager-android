package ru.dimagor555.password.validation

data class PasswordValidationException(
    val validationErrors: List<PasswordValidationError>
) : RuntimeException()