package ru.dimagor555.password.ui.listscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.SingleLineText
import ru.dimagor555.ui.core.component.button.SimpleIconButton
import ru.dimagor555.ui.core.component.topappbar.UnlimitedDefaultTopAppBar
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun PasswordListTopAppBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClearSearchText: () -> Unit,
    favouriteFilter: FavouriteFilter,
    onFavouriteFilterChange: (FavouriteFilter) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    onSortingButtonClicked: () -> Unit
) {
    UnlimitedDefaultTopAppBar {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SimpleIconButton(
                    icon = Icons.Default.Settings,
                    contentDescription = stringResource(MR.strings.settings),
                    onClick = navigateToSettingsScreen
                )
                SearchTextField(
                    modifier = Modifier.weight(1f),
                    searchText = searchText,
                    onSearchTextChange = onSearchTextChange,
                    onClearSearchText = onClearSearchText
                )
                SimpleIconButton(
                    icon = Icons.Default.Sort,
                    contentDescription = stringResource(MR.strings.sorting),
                    onClick = onSortingButtonClicked
                )
            }
            FavouriteFilterTabs(
                selectedFavouriteFilter = favouriteFilter,
                onFavouriteFilterChange = onFavouriteFilterChange
            )
        }
    }
}

@Composable
private fun FavouriteFilterTabs(
    selectedFavouriteFilter: FavouriteFilter,
    onFavouriteFilterChange: (FavouriteFilter) -> Unit
) {
    val tabs = listOf(FavouriteFilter.All, FavouriteFilter.Favourite)
    TabRow(selectedTabIndex = tabs.indexOf(selectedFavouriteFilter)) {
        tabs.forEach {
            val isSelected = it == selectedFavouriteFilter
            Tab(
                selected = isSelected,
                onClick = { onFavouriteFilterChange(it) }
            ) {
                SingleLineText(
                    modifier = Modifier.padding(4.dp),
                    text = getFavouriteFilterTitle(it),
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}

@Composable
private fun getFavouriteFilterTitle(favouriteFilter: FavouriteFilter) =
    when (favouriteFilter) {
        FavouriteFilter.All -> stringResource(MR.strings.all_passwords)
        FavouriteFilter.Favourite -> stringResource(MR.strings.favourite)
    }

@Preview
@Composable
private fun TopAppBarPreview() {
    PasswordManagerTheme {
        PasswordListTopAppBar(
            searchText = "",
            onSearchTextChange = {},
            onClearSearchText = {},
            favouriteFilter = FavouriteFilter.All,
            onFavouriteFilterChange = {},
            navigateToSettingsScreen = {},
            onSortingButtonClicked = {}
        )
    }
}