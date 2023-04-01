package ru.dimagor555.password.ui.listscreen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.password.domain.filter.FilterState
import ru.dimagor555.password.ui.listscreen.components.PasswordListScaffold
import ru.dimagor555.password.ui.listscreen.components.PasswordListScreenContent
import ru.dimagor555.password.ui.listscreen.components.SortingDialog
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.Action
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.State
import ru.dimagor555.password.ui.listscreen.model.PasswordState
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.OnSideEffect
import ru.dimagor555.ui.core.util.createLongSnackbarMessage

@Composable
fun PasswordListScreen(component: PasswordListComponent) {
    component as PasswordListComponentImpl

    val state by component.state.collectAsState()

    PasswordListScaffold(
        filterState = state.filterState,
        sendAction = component::sendAction,
        navigateToPasswordCreationScreen = component.callbacks.navigateToPasswordCreationScreen,
        navigateToSettingsScreen = component.callbacks.navigateToSettingsScreen,
    ) { snackbarHostState ->
        PasswordListScreenContent(
            state = state,
            sendAction = component::sendAction,
            navigateToPasswordDetailsScreen = { id: String ->
                component.callbacks.navigateToPasswordDetailsScreen(id, state.id)
            },
        )
        SortingDialogWrapper(state = state, sendAction = component::sendAction)
        OnSideEffect(
            component = component,
            snackbarHostState = snackbarHostState,
            onSideEffect = { sideEffect, showSnackbar ->
                when (sideEffect) {
                    is PasswordListStore.SideEffect.ShowMessage -> showSnackbar(
                        createLongSnackbarMessage(sideEffect.message)
                    )
                }
            },
        )
    }
}

@Composable
private fun SortingDialogWrapper(
    state: State,
    sendAction: (Action) -> Unit,
) {
    if (state.isSortingDialogVisible)
        SortingDialog(
            sortingType = state.filterState.sortingType,
            onDismiss = { sendAction(Action.ChangeSortingDialogVisibility(isVisible = false)) },
            onChangeSortingType = { sendAction(Action.ChangeSortingType(it)) },
        )
}

@Preview
@Composable
private fun EmptyPasswordListScreenPreview() {
    PasswordManagerTheme {
        PasswordListScaffold(
            filterState = FilterState(),
            navigateToPasswordCreationScreen = {},
            sendAction = {},
            navigateToSettingsScreen = {},
        ) {
            PasswordListScreenContent(
                state = State(isLoading = false),
                sendAction = {},
                navigateToPasswordDetailsScreen = { },
            )
        }
    }
}

@Preview
@Composable
private fun FilledPasswordListScreenPreview() {
    PasswordManagerTheme {
        PasswordListScaffold(
            filterState = FilterState(),
            navigateToPasswordCreationScreen = {},
            sendAction = {},
            navigateToSettingsScreen = {},
        ) {
            PasswordListScreenContent(
                state = State(
                    passwordStates = (0..10).map {
                        PasswordState(
                            passwordId = it.toString(),
                            title = "Test title",
                            uniqueIdentifier = "test login",
                            isFavourite = it % 2 == 1
                        )
                    },
                    isLoading = false
                ),
                sendAction = {},
                navigateToPasswordDetailsScreen = {  },
            )
        }
    }
}