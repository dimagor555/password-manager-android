package ru.dimagor555.password.ui.listscreen.model

import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.Message
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.State

internal class PasswordListReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message) =
        when (msg) {
            is Message.ShowPasswordStates -> copy(passwordStates = msg.passwordStates)
            is Message.ShowLoading -> copy(isLoading = msg.isLoading)
            is Message.ShowFilterState -> copy(filterState = msg.filterState)
            is Message.ChangeSortingDialogVisibility -> copy(isSortingDialogVisible = msg.isVisible)
        }
}
