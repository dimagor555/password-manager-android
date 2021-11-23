package ru.dimagor555.password.editingcore.model

import android.content.Context
import ru.dimagor555.password.domain.validation.ValidationError
import ru.dimagor555.password.editingcore.R
import ru.dimagor555.password.validation.PasswordValidationError

fun ValidationError.toStringMessage(context: Context) =
    when (this) {
        ValidationError.IsBlank ->
            context.getString(R.string.validation_error_is_blank)
        is ValidationError.IsTooLong ->
            context.getString(R.string.validation_error_is_too_long, maxLength)
    }

fun PasswordValidationError.toStringMessage(context: Context) =
    when (this) {
        PasswordValidationError.IsBlank ->
            context.getString(R.string.validation_error_is_blank)
        is PasswordValidationError.IsTooLong ->
            context.getString(R.string.validation_error_is_too_long, maxLength)
        is PasswordValidationError.IsTooShort ->
            context.getString(R.string.validation_error_is_too_short, minLength)
        else -> null
    }