package ru.dimagor555.password.ui.listscreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import ru.dimagor555.password.domain.filter.FavouriteFilter
import ru.dimagor555.password.domain.filter.FilterState
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.Action
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.State
import ru.dimagor555.password.ui.listscreen.model.PasswordState
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.FullscreenInformationContent
import ru.dimagor555.ui.core.component.FullscreenThrottledCircularProgressBar
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun PasswordListScreenContent(
    state: State,
    sendAction: (Action) -> Unit,
    navigateToPasswordDetailsScreen: (passwordId: String) -> Unit,
) {
    when {
        state.isLoading -> FullscreenThrottledCircularProgressBar(visible = true)
        state.hasNoPasswords -> NoPasswords(state.filterState)
        else -> PasswordListWrapper(
            passwordStates = state.passwordStates,
            navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
            onTriggerEvent = sendAction,
        )
    }
}

@Composable
private fun NoPasswords(filterState: FilterState) {
    when {
        filterState.searchText.isNotBlank() ->
            FullscreenInformationContent(
                title = stringResource(MR.strings.not_found_screen_title),
                contentText = stringResource(MR.strings.not_found_screen_content),
                image = Icons.Default.Lock,
            )
        filterState.favouriteFilter == FavouriteFilter.All ->
            FullscreenInformationContent(
                title = stringResource(MR.strings.empty_passwords_screen_title),
                contentText = stringResource(MR.strings.empty_passwords_screen_content),
                image = Icons.Default.Lock,
            )
        filterState.favouriteFilter == FavouriteFilter.Favourite ->
            FullscreenInformationContent(
                title = stringResource(MR.strings.empty_favourite_screen_title),
                contentText = stringResource(MR.strings.empty_favourite_screen_content),
                image = Icons.Default.Lock,
            )
    }
}

@Composable
private fun PasswordListWrapper(
    passwordStates: List<PasswordState>,
    navigateToPasswordDetailsScreen: (passwordId: String) -> Unit,
    onTriggerEvent: (Action) -> Unit,
) {
    PasswordList(
        passwordStates = passwordStates,
        navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
        onToggleFavourite = { onTriggerEvent(Action.ToggleFavourite(it)) },
        onCopyPassword = { onTriggerEvent(Action.CopyPassword(it)) },
    )
}