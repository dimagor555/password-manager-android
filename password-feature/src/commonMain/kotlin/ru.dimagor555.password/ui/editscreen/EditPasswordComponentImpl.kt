package ru.dimagor555.password.ui.editscreen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.dimagor555.core.presentation.componentScope
import ru.dimagor555.core.presentation.getStore
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore
import ru.dimagor555.password.ui.editscreen.EditPasswordComponent.EditPasswordComponentCallbacks
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.Action
import ru.dimagor555.password.ui.editscreen.model.PasswordDto

fun createEditPasswordComponent(
    componentContext: ComponentContext,
    passwordId: Int,
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
    val passwordId: Int,
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
            .map(::mapCommonEditSideEffectToEditAction)
            .onEach { editPasswordStore.sendAction(it) }
            .launchIn(componentScope)
    }

    private fun mapEditSideEffectToCommonEditAction(
        sideEffect: EditPasswordStore.SideEffect
    ): List<CommonEditPasswordStore.Action> =
        when (sideEffect) {
            is EditPasswordStore.SideEffect.PasswordLoaded -> listOf(
                CommonEditPasswordStore.Action.ChangeTitle(sideEffect.password.title),
                CommonEditPasswordStore.Action.ChangeLogin(sideEffect.password.login),
                CommonEditPasswordStore.Action.ChangePassword(sideEffect.password.password)
            )
            EditPasswordStore.SideEffect.RequestValidatePassword -> listOf(
                CommonEditPasswordStore.Action.Validate
            )
        }

    private fun mapCommonEditSideEffectToEditAction(
        sideEffect: CommonEditPasswordStore.SideEffect
    ) = when (sideEffect) {
        is CommonEditPasswordStore.SideEffect.ValidationSucceed ->
            Action.OnPasswordValidationSucceed(
                PasswordDto(
                    sideEffect.title,
                    sideEffect.login,
                    sideEffect.password
                )
            )
        CommonEditPasswordStore.SideEffect.ValidationFailed ->
            Action.OnPasswordValidationFailed
    }

    fun sendAction(action: Action) = editPasswordStore.sendAction(action)

    override fun sendGeneratedPassword(generatedPassword: String) {
        commonEditPasswordStore.sendAction(
            CommonEditPasswordStore.Action.ChangePassword(generatedPassword)
        )
    }
}
