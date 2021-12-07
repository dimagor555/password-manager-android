package ru.dimagor555.password.editingcore.model

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.password.editingcore.R
import ru.dimagor555.password.validation.TextValidationError

internal fun TextValidationError.toLocalizedString(): LocalizedString =
    when (this) {
        TextValidationError.IsBlank -> {
            LocalizedString.resource(R.string.validation_error_is_blank)
        }
        is TextValidationError.IsTooLong -> {
            LocalizedString.quantity(R.plurals.validation_error_is_too_long, maxLength, maxLength)
        }
        is TextValidationError.IsTooShort -> {
            LocalizedString.quantity(R.plurals.validation_error_is_too_short, minLength, minLength)
        }
    }