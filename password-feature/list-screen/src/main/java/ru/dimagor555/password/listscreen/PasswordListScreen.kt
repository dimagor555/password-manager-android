package ru.dimagor555.password.listscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.listscreen.components.PasswordListScaffold
import ru.dimagor555.password.listscreen.components.PasswordListScreenContent
import ru.dimagor555.password.listscreen.components.SideEffectHandler
import ru.dimagor555.password.listscreen.components.SortingDialog
import ru.dimagor555.password.listscreen.model.PasswordListStore.Action
import ru.dimagor555.password.listscreen.model.PasswordListStore.State
import ru.dimagor555.password.listscreen.model.PasswordState
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
        filterState = state.filterState,
        sendAction = viewModel::sendAction,
        navigateToPasswordCreationScreen = navigateToPasswordCreationScreen,
        navigateToSettingsScreen = navigateToSettingsScreen
    ) { onShowSnackbar ->
        PasswordListScreenContent(
            state = state,
            sendAction = viewModel::sendAction,
            navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
        )
        SortingDialogWrapper(state = state, sendAction = viewModel::sendAction)
        SideEffectHandler(viewModel = viewModel, onShowSnackbar = onShowSnackbar)
    }
}

@Composable
private fun SortingDialogWrapper(
    state: State,
    sendAction: (Action) -> Unit
) {
    if (state.isSortingDialogVisible)
        SortingDialog(
            sortingType = state.filterState.sortingType,
            onDismiss = { sendAction(Action.ChangeSortingDialogVisibility(isVisible = false)) },
            onChangeSortingType = { sendAction(Action.ChangeSortingType(it)) }
        )
}

@Preview("Empty password list screen", group = "empty")
@Preview("Empty password list screen (ru)", group = "empty", locale = "ru")
@Preview("Empty password list screen (dark)", group = "empty", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EmptyPasswordListScreenPreview() {
    PasswordManagerTheme {
        PasswordListScaffold(
            filterState = PasswordFilterState(),
            navigateToPasswordCreationScreen = {},
            sendAction = {},
            navigateToSettingsScreen = {}
        ) {
            PasswordListScreenContent(
                state = State(isLoading = false),
                sendAction = {},
                navigateToPasswordDetailsScreen = {}
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
            filterState = PasswordFilterState(),
            navigateToPasswordCreationScreen = {},
            sendAction = {},
            navigateToSettingsScreen = {}
        ) {
            PasswordListScreenContent(
                state = State(
                    passwordStates = (0..10).map {
                        PasswordState(
                            id = it,
                            title = "Test title",
                            login = "test login",
                            isFavourite = it % 2 == 1
                        )
                    },
                    isLoading = false
                ),
                sendAction = {},
                navigateToPasswordDetailsScreen = {}
            )
        }
    }
}