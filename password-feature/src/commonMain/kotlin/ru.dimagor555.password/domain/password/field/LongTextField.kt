package ru.dimagor555.password.domain.password.field

import ru.dimagor555.password.validation.core.LengthSpec
import ru.dimagor555.password.validation.core.TextValidationError
import ru.dimagor555.password.validation.core.TextValidationUtil

data class LongTextField(
    override val key: String,
    override val text: String = "",
) : Field

class LongTextFieldValidator : FieldValidator<LongTextField> {

    override fun validate(field: LongTextField): List<TextValidationError> = TextValidationUtil.validate(
        text = field.text,
        lengthSpec = LengthSpec(maxLength = MAX_TEXT_LENGTH)
    )

    companion object {
        private const val MAX_TEXT_LENGTH = 500
    }
}