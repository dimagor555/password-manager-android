package ru.dimagor555.password.ui.listscreen.components

import androidx.compose.runtime.Composable
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.Action
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.State
import ru.dimagor555.password.ui.listscreen.model.PasswordState
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.FullscreenCircularProgressBar
import ru.dimagor555.ui.core.util.stringResource

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
                title = stringResource(MR.strings.not_found_screen_title),
                contentText = stringResource(MR.strings.not_found_screen_content)
            )
        filterState.favouriteFilter == FavouriteFilter.All ->
            FullscreenInformationContent(
                title = stringResource(MR.strings.empty_passwords_screen_title),
                contentText = stringResource(MR.strings.empty_passwords_screen_content)
            )
        filterState.favouriteFilter == FavouriteFilter.Favourite ->
            FullscreenInformationContent(
                title = stringResource(MR.strings.empty_favourite_screen_title),
                contentText = stringResource(MR.strings.empty_favourite_screen_content)
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