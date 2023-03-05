package ru.dimagor555.password.domain.password.field

import kotlinx.serialization.Serializable
import ru.dimagor555.password.validation.core.LengthSpec
import ru.dimagor555.password.validation.core.TextValidationError
import ru.dimagor555.password.validation.core.TextValidationUtil

@Serializable
data class SiteField(
    override val key: String,
    override val text: String = "",
) : Field

@Serializable
class SiteFieldValidator : FieldValidator<SiteField> {

    override fun validate(field: SiteField): List<TextValidationError> = TextValidationUtil.validate(
        text = field.text,
        lengthSpec = LengthSpec(maxLength = MAX_SITE_LENGTH)
    )

    companion object {
        private const val MAX_SITE_LENGTH = 500
    }
}
