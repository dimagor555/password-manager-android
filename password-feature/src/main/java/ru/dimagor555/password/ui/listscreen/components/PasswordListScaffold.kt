package ru.dimagor555.password.ui.listscreen.components

import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.R
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.Action
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold
import ru.dimagor555.ui.core.util.SnackbarMessage

@Composable
internal fun PasswordListScaffold(
    filterState: PasswordFilterState,
    sendAction: (Action) -> Unit,
    navigateToPasswordCreationScreen: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
    content: @Composable ((SnackbarMessage) -> Unit) -> Unit
) {
    SingleSnackbarScaffold(
        topBar = {
            PasswordListTopAppBar(
                searchText = filterState.searchText,
                onSearchTextChange = { sendAction(Action.ChangeSearchText(it)) },
                onClearSearchText = { sendAction(Action.ClearSearchText) },
                favouriteFilter = filterState.favouriteFilter,
                onFavouriteFilterChange = { sendAction(Action.ChangeFavouriteFilter(it)) },
                navigateToSettingsScreen = navigateToSettingsScreen,
                onSortingButtonClicked = {
                    sendAction(Action.ChangeSortingDialogVisibility(isVisible = true))
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