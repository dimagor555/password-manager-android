package ru.dimagor555.masterpassword.ui.editscreen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.masterpassword.ui.editscreen.EditMasterPasswordComponent.EditMasterPasswordCallbacks
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.State

fun createEditMasterPasswordComponent(
    componentContext: ComponentContext,
    callbacks: EditMasterPasswordCallbacks
): EditMasterPasswordComponent {
    return EditMasterPasswordComponentImpl(componentContext, callbacks)
}

internal class EditMasterPasswordComponentImpl constructor(
    componentContext: ComponentContext,
    val callbacks: EditMasterPasswordCallbacks
) : MviComponentContext<Action, State, Nothing>(
    componentContext = componentContext,
    storeFactory = { EditMasterPasswordStore() },
), EditMasterPasswordComponent {

    private val backCallback = BackCallback {
        sendAction(Action.GoBack)
    }

    init {
        backHandler.register(backCallback)
    }

    override fun sendGeneratedPassword(generatedPassword: String) {
            store.sendAction(Action.ChangePassword(generatedPassword))
    }
}
