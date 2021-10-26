package ru.dimagor555.password.domain.validation

import ru.dimagor555.password.validation.PasswordValidationError

sealed class ValidationException : RuntimeException() {
    data class InvalidPasswordException(
        val validationErrors: List<PasswordValidationError>
    ) : ValidationException()

    data class InvalidTitleException(
        val validationErrors: List<ValidationError>
    ) : ValidationException()

    data class InvalidLoginException(
        val validationErrors: List<ValidationError>
    ) : ValidationException()
}
