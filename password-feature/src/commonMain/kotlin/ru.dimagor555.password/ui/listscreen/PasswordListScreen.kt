package ru.dimagor555.password.ui.listscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.ui.listscreen.components.PasswordListScaffold
import ru.dimagor555.password.ui.listscreen.components.PasswordListScreenContent
import ru.dimagor555.password.ui.listscreen.components.SideEffectHandler
import ru.dimagor555.password.ui.listscreen.components.SortingDialog
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.Action
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.State
import ru.dimagor555.password.ui.listscreen.model.PasswordState
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview

@Composable
fun PasswordListScreen(
    navigateToPasswordDetailsScreen: (Int) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateToPasswordCreationScreen: () -> Unit
) {
    val viewModel = koinViewModel<PasswordListViewModel>()
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

@Preview
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

@Preview
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