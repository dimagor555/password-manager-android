package ru.dimagor555.password.validation.ui

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.password.validation.core.PasswordValidationError
import ru.dimagor555.password.validation.core.TextValidationError
import ru.dimagor555.validation.R

fun TextValidationError.toLocalizedString(): LocalizedString =
    when (this) {
        TextValidationError.IsBlank -> {
            LocalizedString.resource(R.string.error_is_blank)
        }
        is TextValidationError.IsTooLong -> {
            LocalizedString.quantity(R.plurals.error_is_too_long, maxLength, maxLength)
        }
        is TextValidationError.IsTooShort -> {
            LocalizedString.quantity(R.plurals.error_is_too_short, minLength, minLength)
        }
    }

fun PasswordValidationError.toLocalizedString(): LocalizedString =
    LocalizedString.resource(
        when (this) {
            PasswordValidationError.NotContainDigits -> {
                R.string.error_not_contain_digits
            }
            PasswordValidationError.NotContainLowercaseLetters ->{
                R.string.error_not_contain_lowercase_letters
            }
            PasswordValidationError.NotContainUppercaseLetters ->{
                R.string.error_not_contain_uppercase_letters
            }
            PasswordValidationError.NotContainSpecialSymbols ->{
                R.string.error_not_contain_special_symbols
            }
        }
    )