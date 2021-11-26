package ru.dimagor555.password.listscreen.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.listscreen.R
import ru.dimagor555.ui.core.components.SimpleIconButton
import ru.dimagor555.ui.core.components.SingleLineText
import ru.dimagor555.ui.core.components.UnlimitedDefaultTopAppBar
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun PasswordListTopAppBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    favouriteFilter: FavouriteFilter,
    onFavouriteFilterChange: (FavouriteFilter) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    onSortingClicked: () -> Unit
) {
    UnlimitedDefaultTopAppBar {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SimpleIconButton(
                    icon = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings),
                    onClick = navigateToSettingsScreen
                )
                SearchTextField(
                    modifier = Modifier.weight(1f),
                    searchText = searchText,
                    onSearchTextChange = onSearchTextChange
                )
                SimpleIconButton(
                    icon = Icons.Default.Sort,
                    contentDescription = stringResource(R.string.sorting),
                    onClick = onSortingClicked
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
        FavouriteFilter.All -> stringResource(R.string.all_passwords)
        FavouriteFilter.Favourite -> stringResource(R.string.favourite)
    }

@Preview("TopAppBar")
@Preview("TopAppBar (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TopAppBarPreview() {
    PasswordManagerTheme {
        PasswordListTopAppBar(
            searchText = "",
            onSearchTextChange = {},
            favouriteFilter = FavouriteFilter.All,
            onFavouriteFilterChange = {},
            navigateToSettingsScreen = {},
            onSortingClicked = {}
        )
    }
}