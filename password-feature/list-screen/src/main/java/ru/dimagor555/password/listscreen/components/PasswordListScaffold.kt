package ru.dimagor555.password.listscreen.components

import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.listscreen.R
import ru.dimagor555.password.listscreen.model.PasswordListEvent
import ru.dimagor555.password.listscreen.model.PasswordListEvent.*
import ru.dimagor555.ui.core.component.SingleSnackbarSimpleScaffold

@Composable
internal fun PasswordListScaffold(
    filterState: PasswordFilterState,
    sendEvent: (PasswordListEvent) -> Unit,
    navigateToPasswordCreationScreen: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
    content: @Composable (onShowSnackBar: (String, String?) -> Unit) -> Unit
) {
    SingleSnackbarSimpleScaffold(
        topAppBar = {
            PasswordListTopAppBar(
                searchText = filterState.searchText,
                onSearchTextChange = { sendEvent(SearchTextChanged(it)) },
                favouriteFilter = filterState.favouriteFilter,
                onFavouriteFilterChange = { sendEvent(FavouriteFilterChanged(it)) },
                navigateToSettingsScreen = navigateToSettingsScreen,
                onSortingClicked = {
                    sendEvent(UpdateSortingDialogVisibility(UiComponentVisibility.Show))
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