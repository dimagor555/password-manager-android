package ru.dimagor555.password.editingcore.model

import android.content.Context
import ru.dimagor555.password.validation.TextValidationError

internal class TextFieldError(private val validationError: TextValidationError) {
    fun getMessage(context: Context): String = validationError.toStringMessage(context)
}

internal fun TextValidationError.toTextFieldError() = TextFieldError(this)