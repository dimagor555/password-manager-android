package ru.dimagor555.password.ui.commoneditscreen.model

import dev.icerock.moko.resources.desc.StringDesc
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.domain.password.field.Field.Companion.createFieldByKey
import ru.dimagor555.password.domain.password.field.copy
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore.*
import ru.dimagor555.password.validation.core.TextValidationError
import ru.dimagor555.ui.core.model.FieldState

class CommonEditPasswordStore : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = CommonEditPasswordActor(),
    reducer = CommonEditPasswordReducer(),
) {

    internal data class State(
        val parentId: String = Folder.ROOT_FOLDER_ID,
        val fieldsByKeys: Map<String, FieldState> = emptyMap(),
    ) {

        val passwordFields
            get() = PasswordFields(
                fieldsByKeys.mapNotNull {
                    createFieldByKey(it.key)?.copy(it.value.text)
                }.toSet()
            )
    }

    sealed interface Action {

        object Validate : Action

        data class LoadPasswordFields(val fields: PasswordFields) : Action, Message

        data class ChangeFieldByKey(val key: String, val text: String) : Action

        object TogglePasswordVisibility : Action, Message

        data class ShowUpdateErrors(
            val errorsByFieldTypes: Map<String, TextValidationError?>
        ) : Action

        object ShowUnknownUpdateError : Action
    }

    internal sealed interface Message {

        data class ShowFieldText(
            val fieldKey: String,
            val text: String,
        ) : Message

        data class ShowFieldError(
            val fieldKey: String,
            val error: StringDesc?,
        ) : Message
    }

    sealed interface SideEffect {

        data class ValidationSucceed(
            val parentId: String,
            val passwordFields: PasswordFields,
        ) : SideEffect

        object ValidationFailed : SideEffect

        data class ShowUnknownUpdateError(val message: StringDesc) : SideEffect
    }
}
