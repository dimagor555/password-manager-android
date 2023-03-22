package ru.dimagor555.password.usecase.validation

import ru.dimagor555.password.domain.password.field.Field
import ru.dimagor555.password.domain.password.field.FieldValidator
import ru.dimagor555.password.validation.core.TextValidationError

class ValidateFieldUseCase {
    operator fun invoke(field: Field): TextValidationError? {
        val fieldValidator = FieldValidator.getValidatorByFieldType(field)
        return fieldValidator.validate(field).firstOrNull()
    }
}
