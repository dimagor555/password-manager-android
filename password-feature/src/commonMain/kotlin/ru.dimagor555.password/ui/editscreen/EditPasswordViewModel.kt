package ru.dimagor555.password.ui.editscreen

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordUseCases
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore
import ru.dimagor555.password.ui.editscreen.model.EditPasswordUseCases
import ru.dimagor555.password.ui.editscreen.model.PasswordDto

internal class EditPasswordViewModel(
    editPasswordUseCases: EditPasswordUseCases,
    commonEditPasswordUseCases: CommonEditPasswordUseCases,
) : ViewModel() {
    private val editPasswordStore = EditPasswordStore(editPasswordUseCases)
    val commonEditPasswordStore = CommonEditPasswordStore(commonEditPasswordUseCases)

    val state = editPasswordStore.state

    init {
        initStores()
        bindStores()
    }

    private fun initStores() {
        editPasswordStore.init(viewModelScope)
        commonEditPasswordStore.init(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun bindStores() {
        editPasswordStore.sideEffects
            .map(::mapEditSideEffectToCommonEditAction)
            .flatMapLatest { flowOf(*it.toTypedArray()) }
            .onEach { commonEditPasswordStore.sendAction(it) }
            .launchIn(viewModelScope)
        commonEditPasswordStore.sideEffects
            .map(::mapCommonEditSideEffectToEditAction)
            .onEach { editPasswordStore.sendAction(it) }
            .launchIn(viewModelScope)
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
            EditPasswordStore.Action.OnPasswordValidationSucceed(
                PasswordDto(
                    sideEffect.title,
                    sideEffect.login,
                    sideEffect.password
                )
            )
        CommonEditPasswordStore.SideEffect.ValidationFailed ->
            EditPasswordStore.Action.OnPasswordValidationFailed
    }

    fun sendAction(action: EditPasswordStore.Action) = editPasswordStore.sendAction(action)
}
