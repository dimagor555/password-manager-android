package ru.dimagor555.password.ui.detailsscreen.model

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.desc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.toPasswordFields
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.*
import ru.dimagor555.password.usecase.password.single.UpdatePasswordUseCase
import ru.dimagor555.res.core.MR

internal class PasswordDetailsActor : Actor<State, Action, Message, SideEffect>(), KoinComponent {

    private val useCases: PasswordDetailsUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            is Action.LoadPassword -> loadPassword(action.passwordId, action.parentId)
            Action.TogglePasswordVisibility -> togglePasswordVisibility()
            Action.ToggleFavourite -> toggleFavourite(getState().passwordId)
            Action.CopyPassword -> copyPassword(getState().passwordId)
            Action.CopyLogin -> copyLogin(getState().passwordId)
            is Action.ChangeRemoveDialogVisibility -> sendMessage(
                Message.ChangeRemoveDialogVisibility(action.visible)
            )
            Action.RemovePassword -> removePassword()
        }
    }

    private suspend fun loadPassword(passwordId: String, parentId: String) {
        sendMessage(Message.ChangePasswordId(passwordId))
        sendMessage(Message.ChangeParentId(parentId))
        observePassword()
    }

    private suspend fun observePassword() {
        useCases.observePassword(getState().passwordId)
            .collect {
                if (it != null)
                    onNewPassword(it)
                else
                    sendMessage(Message.ExitScreen)
            }
    }

    private suspend fun onNewPassword(password: Password) {
        val passwordState = password.toPasswordState()
        sendMessage(Message.ShowPassword(passwordState))
        updatePasswordTextIfVisible()
        sendMessage(Message.FinishLoading)
    }

    private suspend fun updatePasswordTextIfVisible() {
        val isVisible = getState().passwordState.isPasswordVisible
        if (isVisible)
            updatePasswordTextByVisibility(true)
        else
            updatePasswordTextByVisibility(false)
    }

    private suspend fun togglePasswordVisibility() {
        val isVisible = getState().passwordState.isPasswordVisible
        updatePasswordTextByVisibility(!isVisible)
    }

    private suspend fun updatePasswordTextByVisibility(isVisible: Boolean) {
        val passwordState = getState().passwordState
        val updatedPasswordState = passwordState.updatePasswordText(decryptPassword(), isVisible)
        sendMessage(Message.ShowPassword(updatedPasswordState))
    }

    private suspend fun decryptPassword(): String = useCases.decryptPassword(getState().passwordId)

    private suspend fun toggleFavourite(passwordId: String) {
        useCases.toggleFavourite(passwordId)
    }

    private suspend fun copyPassword(passwordId: String) {
        useCases.copyPassword(passwordId)
        showMessage(MR.strings.password_copied)
    }

    private suspend fun showMessage(resource: StringResource) {
        val message = resource.desc()
        sendSideEffect(SideEffect.ShowMessage(message))
    }

    private suspend fun copyLogin(passwordId: String) {
        useCases.copyLogin(passwordId)
        showMessage(MR.strings.login_copied)
    }

    private suspend fun removePassword() {
        useCases.updatePassword(
            UpdatePasswordUseCase.Params(
                id = getState().passwordId,
                fromId = getState().parentId,
                toId = Folder.ARCHIVE_FOLDER_ID,
                fields = getState().passwordState.fields.toPasswordFields(),
            )
        )
        sendMessage(Message.ExitScreen)
    }
}
