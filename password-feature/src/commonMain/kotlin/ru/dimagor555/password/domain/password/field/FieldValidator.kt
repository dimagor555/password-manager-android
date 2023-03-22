package ru.dimagor555.password.domain.password.field

import ru.dimagor555.password.validation.core.TextValidationError

sealed interface FieldValidator<in T : Field> {

    fun validate(field: T): List<TextValidationError>

    companion object {
        inline fun <reified FV: FieldValidator<F>, F: Field> getValidatorByFieldType(field: F): FV =
            when (field) {
                is ShortTextField -> ShortTextFieldValidator() as FV
                is LongTextField -> LongTextFieldValidator() as FV
                is PhoneField -> PhoneFieldValidator() as FV
                is SiteField -> SiteFieldValidator() as FV
                is EncryptedPasswordField -> EncryptedPasswordFieldValidator() as FV
                else -> error("impossible")
            }
    }
}
