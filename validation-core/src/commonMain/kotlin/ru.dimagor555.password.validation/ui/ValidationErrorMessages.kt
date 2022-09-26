package ru.dimagor555.password.validation.ui

import dev.icerock.moko.resources.desc.*
import dev.icerock.moko.resources.format
import ru.dimagor555.res.core.MR
import ru.dimagor555.password.validation.core.PasswordValidationError
import ru.dimagor555.password.validation.core.TextValidationError

fun TextValidationError.desc(): StringDesc =
    when (this) {
        TextValidationError.IsBlank -> {
            MR.strings.error_is_blank.desc()
        }
        is TextValidationError.IsTooLong -> {
            MR.plurals.error_is_too_short.format(maxLength, maxLength)
        }
        is TextValidationError.IsTooShort -> {
            MR.plurals.error_is_too_short.format(minLength, minLength)
        }
    }

fun PasswordValidationError.desc(): StringDesc =
        when (this) {
            PasswordValidationError.NotContainDigits -> {
                MR.strings.error_not_contain_digits.desc()
            }
            PasswordValidationError.NotContainLowercaseLetters -> {
                MR.strings.error_not_contain_lowercase_letters.desc()
            }
            PasswordValidationError.NotContainUppercaseLetters -> {
                MR.strings.error_not_contain_uppercase_letters.desc()
            }
            PasswordValidationError.NotContainSpecialSymbols -> {
                MR.strings.error_not_contain_special_symbols.desc()
            }
        }
