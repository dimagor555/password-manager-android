package ru.dimagor555.password.ui.commoneditscreen.model

import dev.icerock.moko.resources.desc.desc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.domain.password.field.copy
import ru.dimagor555.password.domain.password.findFieldByKey
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore.*
import ru.dimagor555.password.validation.core.TextValidationError
import ru.dimagor555.password.validation.ui.desc
import ru.dimagor555.res.core.MR

internal class CommonEditPasswordActor : Actor<State, Action, Message, SideEffect>(),
    KoinComponent {

    private val useCases: CommonEditPasswordUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            Action.Validate -> validate()
            is Action.LoadPasswordFields -> sendMessage(action)
            is Action.ChangeFieldByKey -> changeFieldByKey(action.key, action.text)
            is Action.TogglePasswordVisibility -> sendMessage(action)
            is Action.ShowUpdateErrors -> showUpdateErrors(action.errorsByFieldTypes)
            Action.ShowUnknownUpdateError -> showUnknownUpdateError()
        }
    }

    private suspend fun validate() {
        val state = getState()
        val validationResult = validatePassword(state)
        if (hasValidationErrors(validationResult))
            onValidationFail(validationResult, state)
        else
            onValidationSuccess(state)
    }

    private suspend fun validatePassword(state: State): Map<String, TextValidationError?> =
        useCases.validatePassword(
            state.passwordFields
        )

    private fun hasValidationErrors(validationResult: Map<String, TextValidationError?>) =
        validationResult.any {
            it.value != null
        }

    private suspend fun onValidationFail(
        validationResult: Map<String, TextValidationError?>,
        state: State,
    ) {
        showAllValidationErrors(validationResult, state)
        sendSideEffect(SideEffect.ValidationFailed)
    }

    private suspend fun showAllValidationErrors(
        validationResult: Map<String, TextValidationError?>,
        state: State
    ) {
        val errorsByFieldTypes = createErrorsByFieldTypes(validationResult, state)
        errorsByFieldTypes.forEach { (fieldType, error) ->
            sendMessage(Message.ShowFieldError(fieldType, error?.desc()))
        }
    }

    private fun createErrorsByFieldTypes(
        validationResult: Map<String, TextValidationError?>,
        state: State,
    ): Map<String, TextValidationError?> =
        state.passwordFields.fields
            .map { it.key }
            .associateWith { validationResult[it] }

    private suspend fun onValidationSuccess(state: State) {
        sendSideEffect(
            SideEffect.ValidationSucceed(
                state.parentId,
                state.passwordFields,
            )
        )
    }

    private suspend fun changeFieldByKey(
        key: String,
        text: String,
    ) {
        val field = getState().passwordFields.findFieldByKey(key)?.copy(text = text) ?: return
        sendMessage(Message.ShowFieldText(key, text))
        val error = useCases.validateField(field)?.desc()
        sendMessage(Message.ShowFieldError(key, error))
    }

    private suspend fun showUpdateErrors(errors: Map<String, TextValidationError?>) {
        errors.forEach { (fieldType, error) ->
            sendMessage(Message.ShowFieldError(fieldType, error?.desc()))
        }
    }

    private suspend fun showUnknownUpdateError() {
        val message = MR.strings.password_update_error.desc()
        sendSideEffect(SideEffect.ShowUnknownUpdateError(message))
    }
}
