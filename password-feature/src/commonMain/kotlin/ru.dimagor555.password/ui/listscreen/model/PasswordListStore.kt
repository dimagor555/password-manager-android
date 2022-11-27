package ru.dimagor555.password.ui.listscreen.model

import dev.icerock.moko.resources.desc.StringDesc
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.SimpleActionBootstrapper
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.domain.filter.PasswordSortingType
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.*

internal class PasswordListStore : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = PasswordListActor(),
    reducer = PasswordListReducer(),
    bootstrapper = SimpleActionBootstrapper(Action.InitScreen)
) {

    data class State(
        val passwordStates: List<PasswordState> = emptyList(),
        val isLoading: Boolean = true,
        val filterState: PasswordFilterState = PasswordFilterState(),
        val isSortingDialogVisible: Boolean = false
    ) {
        val hasNoPasswords
            get() = passwordStates.isEmpty()
    }

    sealed interface Action {
        object InitScreen : Action

        data class ChangeSearchText(val searchText: String) : Action
        object ClearSearchText : Action

        data class ChangeFavouriteFilter(val favouriteFilter: FavouriteFilter) : Action

        data class ChangeSortingDialogVisibility(val isVisible: Boolean) : Action
        data class ChangeSortingType(val sortingType: PasswordSortingType) : Action

        data class CopyPassword(val passwordId: Int) : Action
        data class ToggleFavourite(val passwordId: Int) : Action
    }

    sealed interface Message {
        data class ShowPasswordStates(val passwordStates: List<PasswordState>) : Message

        data class ShowLoading(val isLoading: Boolean) : Message

        data class ShowFilterState(val filterState: PasswordFilterState) : Message

        data class ChangeSortingDialogVisibility(val isVisible: Boolean) : Message
    }

    sealed interface SideEffect {
        data class ShowMessage(val message: StringDesc) : SideEffect
    }
}