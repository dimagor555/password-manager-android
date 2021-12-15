package ru.dimagor555.password.listscreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.listscreen.R
import ru.dimagor555.password.listscreen.model.PasswordListEvent
import ru.dimagor555.password.listscreen.model.PasswordListViewState
import ru.dimagor555.password.listscreen.model.PasswordViewState
import ru.dimagor555.ui.core.component.FullscreenCircularProgressBar

@Composable
internal fun PasswordListScreenContent(
    state: PasswordListViewState,
    sendEvent: (PasswordListEvent) -> Unit,
    navigateToPasswordDetailsScreen: (id: Int) -> Unit,
    onShowSnackbar: (String, String?) -> Unit
) {
    when {
        state.progressBarState == ProgressBarState.Loading ->
            FullscreenCircularProgressBar()
        state.passwordViewStates.isEmpty() ->
            NoPasswords(state.filterState)
        else ->
            PasswordListWrapper(
                passwordViewStates = state.passwordViewStates,
                navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
                onTriggerEvent = sendEvent,
                showSnackbar = onShowSnackbar
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
    passwordViewStates: List<PasswordViewState>,
    navigateToPasswordDetailsScreen: (Int) -> Unit,
    onTriggerEvent: (PasswordListEvent) -> Unit,
    showSnackbar: (String, String?) -> Unit
) {
    PasswordList(
        passwordViewStates = passwordViewStates,
        navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
        onToggleFavourite = { onTriggerEvent(PasswordListEvent.ToggleFavourite(it)) },
        onCopyPassword = { onTriggerEvent(PasswordListEvent.CopyPassword(it)) },
        showSnackbar = showSnackbar
    )
}