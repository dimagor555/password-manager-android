package ru.dimagor555.password.ui.editscreen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.dimagor555.core.presentation.componentScope
import ru.dimagor555.core.presentation.getStore
import ru.dimagor555.password.domain.password.field.PASSWORD_FIELD_KEY
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore
import ru.dimagor555.password.ui.editscreen.EditPasswordComponent.EditPasswordComponentCallbacks
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.*

fun createEditPasswordComponent(
    componentContext: ComponentContext,
    passwordId: String,
    callbacks: EditPasswordComponentCallbacks,
): EditPasswordComponent {
    return EditPasswordComponentImpl(
        componentContext,
        passwordId,
        callbacks,
    )
}

internal class EditPasswordComponentImpl constructor(
    componentContext: ComponentContext,
    val passwordId: String,
    val callbacks: EditPasswordComponentCallbacks,
) : EditPasswordComponent, ComponentContext by componentContext {

    private val componentScope by componentScope()

    private val backCallback = BackCallback {
        sendAction(Action.RequestExitScreen)
    }

    private val editPasswordStore = instanceKeeper.getStore {
            EditPasswordStore()
        }

    val commonEditPasswordStore = instanceKeeper.getStore {
            CommonEditPasswordStore()
        }

    val editPasswordState = editPasswordStore.state
    val commonEditPasswordState = commonEditPasswordStore.state

    init {
        bindStores()
        backHandler.register(backCallback)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun bindStores() {
        editPasswordStore.sideEffects
            .map(::mapEditSideEffectToCommonEditAction)
            .flatMapLatest { flowOf(*it.toTypedArray()) }
            .onEach { commonEditPasswordStore.sendAction(it) }
            .launchIn(componentScope)
        commonEditPasswordStore.sideEffects
            .mapNotNull(::mapCommonEditSideEffectToEditAction)
            .onEach { editPasswordStore.sendAction(it) }
            .launchIn(componentScope)
    }

    private fun mapEditSideEffectToCommonEditAction(
        sideEffect: SideEffect,
    ): List<CommonEditPasswordStore.Action> =
        when (sideEffect) {
            is SideEffect.PasswordLoaded -> listOf(
                CommonEditPasswordStore.Action.LoadPasswordFields(sideEffect.fields),
            )
            SideEffect.RequestValidatePassword -> listOf(
                CommonEditPasswordStore.Action.Validate
            )
            is SideEffect.RequestShowUpdateErrors -> listOf(
                CommonEditPasswordStore.Action.ShowUpdateErrors(sideEffect.errorsByFieldTypes)
            )
            is SideEffect.ShowUnknownUpdateError -> listOf(
                CommonEditPasswordStore.Action.ShowUnknownUpdateError
            )
        }

    private fun mapCommonEditSideEffectToEditAction(
        sideEffect: CommonEditPasswordStore.SideEffect
    ): Action? = when (sideEffect) {
        is CommonEditPasswordStore.SideEffect.ValidationSucceed ->
            Action.OnPasswordValidationSucceed(
                sideEffect.passwordFields,
            )
        CommonEditPasswordStore.SideEffect.ValidationFailed ->
            Action.OnPasswordValidationFailed
        else -> null
    }

    fun sendAction(action: Action) = editPasswordStore.sendAction(action)

    override fun sendGeneratedPassword(generatedPassword: String) {
        commonEditPasswordStore.sendAction(
            CommonEditPasswordStore.Action.ChangeFieldByKey(PASSWORD_FIELD_KEY, generatedPassword),
        )
    }
}
