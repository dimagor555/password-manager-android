package ru.dimagor555.password.ui.commoneditscreen.model

import dev.icerock.moko.resources.desc.StringDesc
import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore.*
import ru.dimagor555.ui.core.model.FieldState
import ru.dimagor555.ui.core.model.copy
import ru.dimagor555.ui.core.model.toggleVisibility

internal class CommonEditPasswordReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message) =
        when (msg) {
            is Message.ShowFieldText -> showFieldText(msg.fieldType, msg.text)
            is Message.ShowFieldError -> showFieldError(msg.fieldType, msg.error)
            Action.TogglePasswordVisibility -> togglePasswordVisibility()
        }

    private fun State.showFieldText(fieldType: State.FieldType, text: String): State {
        val oldField = fieldsByTypes[fieldType]!!
        val newField = oldField.copy(text = text)
        return updateField(fieldType, newField)
    }

    private fun State.updateField(fieldType: State.FieldType, field: FieldState) = copy(
        fieldsByTypes = fieldsByTypes
            .toMutableMap()
            .apply { this[fieldType] = field }
    )

    private fun State.showFieldError(fieldType: State.FieldType, error: StringDesc?): State {
        val oldField = fieldsByTypes[fieldType]!!
        val newField = oldField.copy(error = error)
        return updateField(fieldType, newField)
    }

    private fun State.togglePasswordVisibility(): State {
        val newField = password.toggleVisibility()
        return updateField(State.FieldType.Password, newField)
    }
}
