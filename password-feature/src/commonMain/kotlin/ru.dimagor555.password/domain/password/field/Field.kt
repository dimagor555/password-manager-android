package ru.dimagor555.password.domain.password.field

import kotlinx.serialization.Serializable
import ru.dimagor555.ui.core.model.FieldState

@Serializable
sealed interface Field {
    val key: String
    val text: String

    companion object {
        fun createFieldByKey(key: String, text: String = ""): Field? = when (key) {
            TITLE_FIELD_KEY -> ShortTextField(key, text)
            LOGIN_FIELD_KEY -> ShortTextField(key, text)
            PHONE_FIELD_KEY -> PhoneField(key, text)
            PASSWORD_FIELD_KEY -> EncryptedPasswordField(key, text)
            SITE_FIELD_KEY -> SiteField(key, text)
            else -> null
        }
    }
}

fun Field.copy(
    key: String = this.key,
    text: String = this.text,
): Field = when (this) {
    is EncryptedPasswordField -> copy(key = key, text = text)
    is LongTextField -> copy(key = key, text = text)
    is PhoneField -> copy(key = key, text = text)
    is ShortTextField -> copy(key = key, text = text)
    is SiteField -> copy(key = key, text = text)
}

fun Field.getState(): FieldState = when (this) {
    is ShortTextField -> FieldState.Text(text = text)
    is LongTextField -> FieldState.Text(text = text)
    is PhoneField -> FieldState.Text(text = text)
    is EncryptedPasswordField -> FieldState.Password(text = text)
    is SiteField -> FieldState.Text(text = text)
}
