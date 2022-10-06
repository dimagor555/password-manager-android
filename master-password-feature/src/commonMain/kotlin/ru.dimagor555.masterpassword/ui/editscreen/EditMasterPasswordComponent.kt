package ru.dimagor555.masterpassword.ui.editscreen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.State

fun createEditMasterPasswordComponent(
    componentContext: ComponentContext,
    generatedPassword: String?,
    callbacks: EditMasterPassword.EditMasterPasswordCallbacks
): EditMasterPassword {
    return EditMasterPasswordComponent(componentContext, generatedPassword, callbacks)
}

internal class EditMasterPasswordComponent constructor(
    componentContext: ComponentContext,
    val generatedPassword: String?,
    private val callbacks: EditMasterPassword.EditMasterPasswordCallbacks
) : MviComponentContext<Action, State, Nothing>(
    componentContext = componentContext,
    storeFactory = { EditMasterPasswordStore() },
), EditMasterPassword {

    private val backCallback = BackCallback {
        sendAction(Action.GoBack)
    }

    init {
        backHandler.register(backCallback)
    }

    fun navigateToOverviewScreen() {
        callbacks.onSuccess()
    }

    fun navigateBack() {
        callbacks.onCancel()
    }

    fun navigateToPasswordGenerationScreen() {
        callbacks.onNavigateToPasswordGenerationScreen()
    }
}
