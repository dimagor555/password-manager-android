package ru.dimagor555.password.listscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.listscreen.components.FullscreenInformationContent
import ru.dimagor555.password.listscreen.components.PasswordListContent
import ru.dimagor555.password.listscreen.components.PasswordListTopAppBar
import ru.dimagor555.password.listscreen.components.SortingDialog
import ru.dimagor555.password.listscreen.model.FilterViewState
import ru.dimagor555.password.listscreen.model.PasswordListEvent
import ru.dimagor555.password.listscreen.model.PasswordListEvent.*
import ru.dimagor555.password.listscreen.model.PasswordListViewState
import ru.dimagor555.password.listscreen.model.PasswordViewState
import ru.dimagor555.ui.core.components.FullscreenCircularProgressBar
import ru.dimagor555.ui.core.components.SingleSnackbarSimpleScaffold
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
        onTriggerEvent = viewModel::onTriggerEvent,
        navigateToPasswordCreationScreen = navigateToPasswordCreationScreen,
        navigateToSettingsScreen = navigateToSettingsScreen
    ) { onShowSnackbar ->
        PasswordListScreenContent(
            state = state,
            onTriggerEvent = viewModel::onTriggerEvent,
            navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
            onShowSnackbar = onShowSnackbar
        )
        SortingDialogContent(state = state, onTriggerEvent = viewModel::onTriggerEvent)
    }
}

@Composable
private fun PasswordListScaffold(
    filterState: FilterViewState,
    onTriggerEvent: (PasswordListEvent) -> Unit,
    navigateToPasswordCreationScreen: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
    content: @Composable (onShowSnackBar: (String, String?) -> Unit) -> Unit
) {
    SingleSnackbarSimpleScaffold(
        topAppBar = {
            PasswordListTopAppBar(
                searchText = filterState.searchText,
                onSearchTextChange = { onTriggerEvent(SearchTextChanged(it)) },
                favouriteFilter = filterState.favouriteFilter,
                onFavouriteFilterChange = { onTriggerEvent(FavouriteFilterChanged(it)) },
                navigateToSettingsScreen = navigateToSettingsScreen,
                onSortingClicked = {
                    onTriggerEvent(UpdateSortingDialogVisibility(UiComponentVisibility.Show))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToPasswordCreationScreen) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { _, onShowSnackbar ->
        content(onShowSnackbar)
    }
}

@Composable
private fun PasswordListScreenContent(
    state: PasswordListViewState,
    onTriggerEvent: (PasswordListEvent) -> Unit,
    navigateToPasswordDetailsScreen: (id: Int) -> Unit,
    onShowSnackbar: (String, String?) -> Unit
) {
    when {
        state.progressBarState == ProgressBarState.Loading ->
            FullscreenCircularProgressBar()
        state.passwordViewStates.isEmpty() ->
            NoPasswords(state.filterViewState)
        else ->
            PasswordList(
                passwordViewStates = state.passwordViewStates,
                navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
                onTriggerEvent = onTriggerEvent,
                showSnackbar = onShowSnackbar
            )
    }
}

@Composable
private fun NoPasswords(filterState: FilterViewState) {
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
private fun PasswordList(
    passwordViewStates: List<PasswordViewState>,
    navigateToPasswordDetailsScreen: (Int) -> Unit,
    onTriggerEvent: (PasswordListEvent) -> Unit,
    showSnackbar: (String, String?) -> Unit
) {
    PasswordListContent(
        passwordViewStates = passwordViewStates,
        navigateToPasswordDetailsScreen = navigateToPasswordDetailsScreen,
        onToggleFavourite = { onTriggerEvent(ToggleFavourite(it)) },
        onCopyPassword = { onTriggerEvent(CopyPassword(it)) },
        showSnackbar = showSnackbar
    )
}

@Composable
private fun SortingDialogContent(
    state: PasswordListViewState,
    onTriggerEvent: (PasswordListEvent) -> Unit
) {
    if (state.sortingDialogVisibility == UiComponentVisibility.Show)
        SortingDialog(
            sortingType = state.sortingType,
            onDismiss = {
                onTriggerEvent(UpdateSortingDialogVisibility(UiComponentVisibility.Hide))
            },
            onChangeSortingType = { onTriggerEvent(SortingTypeChanged(it)) }
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
            onTriggerEvent = {},
            navigateToSettingsScreen = {}
        ) { onShowSnackbar ->
            PasswordListScreenContent(
                state = PasswordListViewState(progressBarState = ProgressBarState.Idle),
                onTriggerEvent = {},
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
            onTriggerEvent = {},
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
                onTriggerEvent = {},
                navigateToPasswordDetailsScreen = {},
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}