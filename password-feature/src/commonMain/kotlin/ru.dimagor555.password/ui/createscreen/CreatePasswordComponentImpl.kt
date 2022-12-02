package ru.dimagor555.password.ui.createscreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.dimagor555.core.presentation.componentScope
import ru.dimagor555.core.presentation.getStore
import ru.dimagor555.password.domain.password.field.PASSWORD_FIELD_KEY
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore
import ru.dimagor555.password.ui.createscreen.CreatePasswordComponent.CreatePasswordComponentCallbacks
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordStore
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordStore.Action

fun createCreatePasswordComponent(
    componentContext: ComponentContext,
    callbacks: CreatePasswordComponentCallbacks,
): CreatePasswordComponent =
    CreatePasswordComponentImpl(
        componentContext,
        callbacks,
    )

internal class CreatePasswordComponentImpl constructor(
    componentContext: ComponentContext,
    val callbacks: CreatePasswordComponentCallbacks,
) : CreatePasswordComponent, ComponentContext by componentContext {

    private val componentScope by componentScope()

    private val createPasswordStore = instanceKeeper.getStore {
        CreatePasswordStore()
    }

    val commonEditPasswordStore = instanceKeeper.getStore {
        CommonEditPasswordStore()
    }

    val passwordState = createPasswordStore.state
    val commonEditPasswordState = commonEditPasswordStore.state

    init {
        bindStores()
    }

    private fun bindStores() {
        commonEditPasswordStore.sideEffects
            .onEach { onCommonEditPasswordSideEffect(it) }
            .launchIn(componentScope)
    }

    private fun onCommonEditPasswordSideEffect(sideEffect: CommonEditPasswordStore.SideEffect) {
        if (sideEffect is CommonEditPasswordStore.SideEffect.ValidationSucceed) {
            createPasswordStore.sendAction(
                Action.CreatePassword(sideEffect.parentId, sideEffect.passwordFields),
            )
        }
    }

    override fun sendGeneratedPassword(generatedPassword: String) {
        commonEditPasswordStore.sendAction(
        CommonEditPasswordStore.Action.ChangeFieldByKey(PASSWORD_FIELD_KEY, generatedPassword)
        )
    }
}
