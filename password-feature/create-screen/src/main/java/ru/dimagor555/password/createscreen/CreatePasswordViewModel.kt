package ru.dimagor555.password.createscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.dimagor555.password.commonedit.model.CommonEditPasswordStore
import ru.dimagor555.password.commonedit.model.CommonEditPasswordUseCases
import ru.dimagor555.password.createscreen.model.CreatePasswordStore
import ru.dimagor555.password.createscreen.model.CreatePasswordStore.Action
import ru.dimagor555.password.createscreen.model.CreatePasswordUseCases
import javax.inject.Inject

@HiltViewModel
internal class CreatePasswordViewModel @Inject constructor(
    createPasswordUseCases: CreatePasswordUseCases,
    commonEditPasswordUseCases: CommonEditPasswordUseCases
) : ViewModel() {
    private val createPasswordStore = CreatePasswordStore(createPasswordUseCases)
    val commonEditPasswordStore = CommonEditPasswordStore(commonEditPasswordUseCases)

    val state = createPasswordStore.state

    init {
        initStores()
        bindStores()
    }

    private fun initStores() {
        createPasswordStore.init(viewModelScope)
        commonEditPasswordStore.init(viewModelScope)
    }

    private fun bindStores() {
        commonEditPasswordStore.sideEffects
            .onEach { onCommonEditPasswordSideEffect(it) }
            .launchIn(viewModelScope)
    }

    private fun onCommonEditPasswordSideEffect(sideEffect: CommonEditPasswordStore.SideEffect) {
        if (sideEffect is CommonEditPasswordStore.SideEffect.ValidationSucceed)
            createPasswordStore.sendAction(
                Action.CreatePassword(sideEffect.title, sideEffect.login, sideEffect.password)
            )
    }
}