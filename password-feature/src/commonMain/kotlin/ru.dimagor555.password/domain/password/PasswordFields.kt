package ru.dimagor555.password.domain.password

import ru.dimagor555.password.domain.password.field.*
import ru.dimagor555.password.domain.password.field.SiteField
import ru.dimagor555.password.validation.core.TextValidationError

@JvmInline
value class PasswordFields(val fields: Set<Field>) {

    init {
        require(fields.distinctBy { it.key }.size == fields.size) {
            "Sequence contain duplicate keys"
        }
        require(validateRequiredFields()) {
            "Sequence does not contain the required keys"
        }
    }

    private fun validateRequiredFields(): Boolean {
        val validationResults = listOf(
            SingleKeyValidator(TITLE_FIELD_KEY),
            SingleKeyValidator(PASSWORD_FIELD_KEY),
            AnyKeyValidator(listOf(PHONE_FIELD_KEY, LOGIN_FIELD_KEY)),
        )
        return validationResults.all { it.validate(fields) }
    }

    sealed interface RequiredFieldValidator {
        fun validate(fields: Set<Field>): Boolean
    }

    class SingleKeyValidator(private val key: String) : RequiredFieldValidator {
        override fun validate(fields: Set<Field>): Boolean =
            fields.any { it.key == key }
    }

    class AnyKeyValidator(private val keys: List<String>) : RequiredFieldValidator {
        override fun validate(fields: Set<Field>): Boolean =
            fields.any { it.key in keys }
    }
}

fun PasswordFields.toMap() = this.fields.associate {
    it.key to it.text
}

inline fun <reified T : Field> PasswordFields.updateFieldByKey(
    key: String,
    update: (T) -> T
): PasswordFields {
    val updatedFields = fields.map {
        if (it is T && it.key == key) {
            update(it)
        } else {
            it
        }
    }
    return PasswordFields(updatedFields.toSet())
}

fun PasswordFields.addFieldByKey(key: String): PasswordFields =
    when (val field = Field.createFieldByKey(key)) {
        null -> this
        else -> PasswordFields(fields + field)
    }

fun PasswordFields.deleteFieldByKey(key: String): PasswordFields =
    PasswordFields(fields.filter { it.key != key }.toSet())

fun PasswordFields.validate(): List<TextValidationError> = fields
    .flatMap { findFieldErrors(it) }

fun findFieldErrors(field: Field): List<TextValidationError> =
    FieldValidator.getValidatorByFieldType(field).validate(field)

fun findFieldError(field: Field): TextValidationError? =
    findFieldErrors(field).firstOrNull()

fun PasswordFields.findFieldByKey(key: String): Field? =
    this.fields.find { it.key == key }

val PasswordFields.site: SiteField?
    get() = this.fields.find { it.key == SITE_FIELD_KEY } as? SiteField

val PasswordFields.title: ShortTextField
    get() = this.fields.find { it.key == TITLE_FIELD_KEY } as ShortTextField

val PasswordFields.login: ShortTextField?
    get() = this.fields.find { it.key == LOGIN_FIELD_KEY } as? ShortTextField

val PasswordFields.phone: PhoneField?
    get() = this.fields.find { it.key == PHONE_FIELD_KEY } as? PhoneField

val PasswordFields.encryptedPassword: EncryptedPasswordField
    get() = this.fields.find { it.key == PASSWORD_FIELD_KEY } as EncryptedPasswordField
