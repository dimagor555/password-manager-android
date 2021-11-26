package ru.dimagor555.password.listscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.listscreen.components.PasswordListScaffold
import ru.dimagor555.password.listscreen.components.PasswordListScreenContent
import ru.dimagor555.password.listscreen.components.SortingDialog
import ru.dimagor555.password.listscreen.model.FilterViewState
import ru.dimagor555.password.listscreen.model.PasswordListEvent
import ru.dimagor555.password.listscreen.model.PasswordListEvent.SortingTypeChanged
import ru.dimagor555.password.listscreen.model.PasswordListEvent.UpdateSortingDialogVisibility
import ru.dimagor555.password.listscreen.model.PasswordListViewState
import ru.dimagor555.password.listscreen.model.PasswordViewState
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
fun PasswordListScreen(
    navigateToPasswordDetailsScreen: (Int) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateToPasswordCreationScreen: () -> Unit
) {
    val viewModel: PasswordListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    PasswordListScaffold(
        filterState = state.filterViewState,
        sendEvent = viewModel::sendEvent,
        navigateToPasswordCreationScreen = navigateToPasswordCreationScreen,
        navigateToSettingsScreen = navigateToSettingsScreen
    ) { onShowSnackbar ->
        PasswordListScreenContent(
            state = state,
            sendEvent = viewModel::sendEvent,
            navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
            onShowSnackbar = onShowSnackbar
        )
        SortingDialogWrapper(state = state, sendEvent = viewModel::sendEvent)
    }
}

@Composable
private fun SortingDialogWrapper(
    state: PasswordListViewState,
    sendEvent: (PasswordListEvent) -> Unit
) {
    if (state.sortingDialogVisibility == UiComponentVisibility.Show)
        SortingDialog(
            sortingType = state.sortingType,
            onDismiss = {
                sendEvent(UpdateSortingDialogVisibility(UiComponentVisibility.Hide))
            },
            onChangeSortingType = { sendEvent(SortingTypeChanged(it)) }
        )
}

@Preview("Empty password list screen", group = "empty")
@Preview("Empty password list screen (ru)", group = "empty", locale = "ru")
@Preview("Empty password list screen (dark)", group = "empty", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EmptyPasswordListScreenPreview() {
    PasswordManagerTheme {
        PasswordListScaffold(
            filterState = FilterViewState(),
            navigateToPasswordCreationScreen = {},
            sendEvent = {},
            navigateToSettingsScreen = {}
        ) { onShowSnackbar ->
            PasswordListScreenContent(
                state = PasswordListViewState(progressBarState = ProgressBarState.Idle),
                sendEvent = {},
                navigateToPasswordDetailsScreen = {},
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}

@Preview("Filled password list screen", group = "filled")
@Preview("Filled password list screen (ru)", group = "filled", locale = "ru")
@Preview("Filled password list screen (dark)", group = "filled", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FilledPasswordListScreenPreview() {
    PasswordManagerTheme {
        PasswordListScaffold(
            filterState = FilterViewState(),
            navigateToPasswordCreationScreen = {},
            sendEvent = {},
            navigateToSettingsScreen = {}
        ) { onShowSnackbar ->
            PasswordListScreenContent(
                state = PasswordListViewState(
                    progressBarState = ProgressBarState.Idle,
                    passwordViewStates = (0..10).map {
                        PasswordViewState(
                            id = it,
                            title = "Test title",
                            login = "test login",
                            isFavourite = it % 2 == 1
                        )
                    }
                ),
                sendEvent = {},
                navigateToPasswordDetailsScreen = {},
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}