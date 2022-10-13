package ru.dimagor555.password.ui.commoneditscreen.model

import dev.icerock.moko.resources.desc.StringDesc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore.*
import ru.dimagor555.password.usecase.ValidatePasswordUseCase
import ru.dimagor555.password.validation.ui.desc

internal class CommonEditPasswordActor : Actor<State, Action, Message, SideEffect>(), KoinComponent {

    private val useCases: CommonEditPasswordUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            Action.Validate -> validate()
            is Action.ChangeTitle -> changeTitle(action.title)
            is Action.ChangeLogin -> changeLogin(action.login)
            is Action.ChangePassword -> changePassword(action.password)
            is Action.TogglePasswordVisibility -> sendMessage(action)
        }
    }

    private suspend fun validate() {
        val state = getState()
        val validationResult = validatePassword(state)
        if (hasValidationErrors(validationResult))
            onValidationFail(validationResult)
        else
            onValidationSuccess(state)
    }

    private suspend fun validatePassword(state: State) = with(state) {
        useCases.validatePassword(
            ValidatePasswordUseCase.Params(title.text, login.text, password.text)
        )
    }

    private fun hasValidationErrors(validationResult: ValidatePasswordUseCase.Result) =
        validationResult.titleError != null ||
                validationResult.loginError != null ||
                validationResult.passwordError != null

    private suspend fun onValidationFail(validationResult: ValidatePasswordUseCase.Result) {
        showAllValidationErrors(validationResult)
        sendSideEffect(SideEffect.ValidationFailed)
    }

    private suspend fun showAllValidationErrors(validationResult: ValidatePasswordUseCase.Result) {
        val errorsByFieldTypes = createErrorsByFieldTypes(validationResult)
        errorsByFieldTypes.forEach { (fieldType, error) ->
            sendMessage(Message.ShowFieldError(fieldType, error?.desc()))
        }
    }

    private fun createErrorsByFieldTypes(validationResult: ValidatePasswordUseCase.Result) = mapOf(
        State.FieldType.Title to validationResult.titleError,
        State.FieldType.Login to validationResult.loginError,
        State.FieldType.Password to validationResult.passwordError
    )

    private suspend fun onValidationSuccess(state: State) {
        sendSideEffect(
            SideEffect.ValidationSucceed(
                title = state.title.text,
                login = state.login.text,
                password = state.password.text
            )
        )
    }

    private suspend fun changeTitle(title: String) = changeField(
        text = title,
        fieldType = State.FieldType.Title,
        validator = { useCases.validateTitle(it)?.desc() }
    )

    private suspend fun changeField(
        text: String,
        fieldType: State.FieldType,
        validator: suspend (String) -> StringDesc?
    ) {
        sendMessage(Message.ShowFieldText(fieldType, text))
        val error = validator(text)
        sendMessage(Message.ShowFieldError(fieldType, error))
    }

    private suspend fun changeLogin(login: String) = changeField(
        text = login,
        fieldType = State.FieldType.Login,
        validator = { useCases.validateLogin(it)?.desc() }
    )

    private suspend fun changePassword(password: String) = changeField(
        text = password,
        fieldType = State.FieldType.Password,
        validator = { useCases.validatePasswordText(it)?.desc() }
    )
}
