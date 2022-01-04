package ru.dimagor555.password.listscreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.listscreen.R
import ru.dimagor555.password.listscreen.model.PasswordListStore.Action
import ru.dimagor555.password.listscreen.model.PasswordListStore.State
import ru.dimagor555.password.listscreen.model.PasswordState
import ru.dimagor555.ui.core.component.FullscreenCircularProgressBar

@Composable
internal fun PasswordListScreenContent(
    state: State,
    sendAction: (Action) -> Unit,
    navigateToPasswordDetailsScreen: (id: Int) -> Unit,
) {
    when {
        state.isLoading -> FullscreenCircularProgressBar()
        state.hasNoPasswords -> NoPasswords(state.filterState)
        else -> PasswordListWrapper(
            passwordStates = state.passwordStates,
            navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
            onTriggerEvent = sendAction,
        )
    }
}

@Composable
private fun NoPasswords(filterState: PasswordFilterState) {
    when {
        filterState.searchText.isNotBlank() ->
            FullscreenInformationContent(
                title = stringResource(R.string.not_found_screen_title),
                contentText = stringResource(R.string.not_found_screen_content)
            )
        filterState.favouriteFilter == FavouriteFilter.All ->
            FullscreenInformationContent(
                title = stringResource(R.string.empty_passwords_screen_title),
                contentText = stringResource(R.string.empty_passwords_screen_content)
            )
        filterState.favouriteFilter == FavouriteFilter.Favourite ->
            FullscreenInformationContent(
                title = stringResource(R.string.empty_favourite_screen_title),
                contentText = stringResource(R.string.empty_favourite_screen_content)
            )
    }
}

@Composable
private fun PasswordListWrapper(
    passwordStates: List<PasswordState>,
    navigateToPasswordDetailsScreen: (Int) -> Unit,
    onTriggerEvent: (Action) -> Unit,
) {
    PasswordList(
        passwordStates = passwordStates,
        navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
        onToggleFavourite = { onTriggerEvent(Action.ToggleFavourite(it)) },
        onCopyPassword = { onTriggerEvent(Action.CopyPassword(it)) },
    )
}