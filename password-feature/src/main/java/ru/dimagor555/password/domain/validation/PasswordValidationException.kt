package ru.dimagor555.password.domain.validation

import ru.dimagor555.password.validation.core.TextValidationError

data class PasswordValidationException(
    val titleErrors: List<TextValidationError>,
    val loginErrors: List<TextValidationError>,
    val passwordErrors: List<TextValidationError>
) : RuntimeException()
