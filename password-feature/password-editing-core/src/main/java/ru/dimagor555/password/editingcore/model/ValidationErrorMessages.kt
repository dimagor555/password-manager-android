package ru.dimagor555.password.editingcore.model

import android.content.Context
import ru.dimagor555.password.editingcore.R
import ru.dimagor555.password.validation.TextValidationError

internal fun TextValidationError.toStringMessage(context: Context) =
    when (this) {
        TextValidationError.IsBlank ->
            context.getString(R.string.validation_error_is_blank)
        is TextValidationError.IsTooLong ->
            context.resources.getQuantityString(
                R.plurals.validation_error_is_too_long,
                maxLength,
                maxLength
            )
        is TextValidationError.IsTooShort ->
            context.resources.getQuantityString(
                R.plurals.validation_error_is_too_short,
                minLength,
                minLength
            )
    }