package ru.dimagor555.password.domain.password.field

import kotlinx.serialization.Serializable
import ru.dimagor555.password.validation.core.LengthSpec
import ru.dimagor555.password.validation.core.TextValidationError
import ru.dimagor555.password.validation.core.TextValidationUtil

@Serializable
data class ShortTextField(
    override val key: String,
    override val text: String = "",
) : Field

class ShortTextFieldValidator : FieldValidator<ShortTextField> {

    override fun validate(field: ShortTextField): List<TextValidationError> = TextValidationUtil.validate(
        text = field.text,
        lengthSpec = LengthSpec(maxLength = MAX_TEXT_LENGTH)
    )

    companion object {
        private const val MAX_TEXT_LENGTH = 100
    }
}