package ru.dimagor555.password.ui.commoneditscreen.model

import dev.icerock.moko.resources.desc.StringDesc
import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.domain.password.field.PASSWORD_FIELD_KEY
import ru.dimagor555.password.domain.password.field.getState
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore.*
import ru.dimagor555.ui.core.model.FieldState
import ru.dimagor555.ui.core.model.copy

internal class CommonEditPasswordReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message) =
        when (msg) {
            is Message.ShowFieldText -> showFieldText(msg.fieldKey, msg.text)
            is Message.ShowFieldError -> showFieldError(msg.fieldKey, msg.error)
            is Action.LoadPasswordFields -> loadPasswordFields(msg.fields)
            Action.TogglePasswordVisibility -> togglePasswordVisibility()
        }

    private fun State.showFieldText(fieldKey: String, text: String): State {
        val oldField = fieldsByKeys[fieldKey]!!
        val newField = oldField.copy(text = text)
        return updateField(fieldKey, newField)
    }

    private fun State.updateField(fieldKey: String, field: FieldState) = copy(
        fieldsByKeys = fieldsByKeys
            .toMutableMap()
            .apply { this[fieldKey] = field }
    )

    private fun State.showFieldError(fieldKey: String, error: StringDesc?): State {
        val oldField = fieldsByKeys[fieldKey]!!
        val newField = oldField.copy(error = error)
        return updateField(fieldKey, newField)
    }

    private fun State.loadPasswordFields(fields: PasswordFields): State = this.copy(
        fieldsByKeys = fields.fields.associate {
            it.key to it.getState()
        }
    )

    private fun State.togglePasswordVisibility(): State {
        val newField = fieldsByKeys[PASSWORD_FIELD_KEY]!!
        return updateField(PASSWORD_FIELD_KEY, newField)
    }
}
