package ru.dimagor555.password.ui.listscreen.model

import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.filter.PasswordSortingType
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.*
import ru.dimagor555.res.core.MR

internal class PasswordListActor : Actor<State, Action, Message, SideEffect>(), KoinComponent {

    private val useCases: PasswordListUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            Action.InitScreen -> initScreen()
            is Action.ChangeSearchText -> changeSearchText(action.searchText)
            Action.ClearSearchText -> clearSearchText()
            is Action.ChangeFavouriteFilter -> changeFavouriteFilter(action.favouriteFilter)
            is Action.ChangeSortingDialogVisibility -> sendMessage(
                Message.ChangeSortingDialogVisibility(action.isVisible)
            )
            is Action.ChangeSortingType -> changeSortingType(action.sortingType)
            is Action.CopyPassword -> copyPassword(action.passwordId)
            is Action.ToggleFavourite -> toggleFavourite(action.passwordId)
        }
    }

    private suspend fun initScreen() = coroutineScope {
        launch { observePasswords() }
        launch { observePasswordFilter() }
    }

    private suspend fun observePasswords() {
        useCases.observePasswords()
            .collect { onNewPasswords(it) }
    }

    private suspend fun onNewPasswords(passwords: List<Password>) {
        val passwordStates = passwords.toPasswordStates()
        sendMessage(Message.ShowPasswordStates(passwordStates))
        sendMessage(Message.ShowLoading(isLoading = false))
    }

    private suspend fun observePasswordFilter() {
        useCases.observePasswordFilterState()
            .collect { sendMessage(Message.ShowFilterState(it)) }
    }

    private suspend fun changeSearchText(searchText: String) {
        useCases.updatePasswordFilterState(
            getState().filterState.copy(searchText = searchText)
        )
    }

    private suspend fun clearSearchText() {
        changeSearchText("")
        sendMessage(Message.ShowLoading(isLoading = true))
    }

    private suspend fun changeFavouriteFilter(favouriteFilter: FavouriteFilter) {
        useCases.updatePasswordFilterState(
            getState().filterState.copy(favouriteFilter = favouriteFilter)
        )
        sendMessage(Message.ShowLoading(isLoading = true))
    }

    private suspend fun changeSortingType(sortingType: PasswordSortingType) {
        useCases.updatePasswordFilterState(
            getState().filterState.copy(sortingType = sortingType)
        )
    }

    private suspend fun copyPassword(passwordId: Int) {
        useCases.copyPassword(passwordId)
        showPasswordCopiedMessage()
    }

    private suspend fun showPasswordCopiedMessage() {
        val message = MR.strings.password_copied.desc()
        sendSideEffect(SideEffect.ShowMessage(message))
    }

    private suspend fun toggleFavourite(passwordId: Int) {
        useCases.toggleFavourite(passwordId)
    }
}
