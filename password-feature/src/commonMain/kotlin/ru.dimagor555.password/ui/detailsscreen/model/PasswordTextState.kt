package ru.dimagor555.password.ui.detailsscreen.model

import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.field.PASSWORD_FIELD_KEY
import ru.dimagor555.password.domain.password.toMap

internal data class PasswordState(
    val fields: Map<String, String> = emptyMap(),
    val isPasswordVisible: Boolean = false,
    val isFavourite: Boolean = false,
)

internal fun PasswordState.updatePasswordText(text: String, isVisible: Boolean): PasswordState {
    val mutableFields = fields.toMutableMap()
    val passwordText = if (isVisible) text else HiddenPasswordText
    mutableFields[PASSWORD_FIELD_KEY] = passwordText

    return this.copy(
        fields = mutableFields,
        isPasswordVisible = isVisible,
    )
}

internal fun Password.toPasswordState() = PasswordState(
    fields = fields.toMap(),
    isPasswordVisible = false,
    isFavourite = metadata.isFavourite,
)

private const val HiddenPasswordText = "●●●●●●●●●●"
