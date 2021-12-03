package ru.dimagor555.password.listscreen.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.domain.filter.PasswordSortingType
import ru.dimagor555.password.listscreen.R
import ru.dimagor555.ui.core.components.DefaultDialog
import ru.dimagor555.ui.core.components.RadioTextOptionGroup
import ru.dimagor555.ui.core.components.SingleLineText
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun SortingDialog(
    sortingType: PasswordSortingType,
    onChangeSortingType: (PasswordSortingType) -> Unit,
    onDismiss: () -> Unit
) {
    DefaultDialog(onDismissRequest = onDismiss) {
        SortingDialogContent(
            sortingType = sortingType,
            onDismiss = onDismiss,
            onChangeSortingType = onChangeSortingType,
        )
    }
}

@Composable
private fun SortingDialogContent(
    sortingType: PasswordSortingType,
    onDismiss: () -> Unit,
    onChangeSortingType: (PasswordSortingType) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SingleLineText(
            text = stringResource(R.string.sort_by),
            style = MaterialTheme.typography.subtitle1
        )
        SortingOptionGroup(
            sortingType = sortingType,
            onOptionSelected = {
                onChangeSortingType(it)
                onDismiss()
            }
        )
    }
}

@Composable
private fun SortingOptionGroup(
    sortingType: PasswordSortingType,
    onOptionSelected: (PasswordSortingType) -> Unit
) {
    RadioTextOptionGroup(
        options = PasswordSortingType.values(),
        selectedOption = sortingType,
        onOptionSelected = onOptionSelected,
        getOptionTitle = { getSortingTypeTitle(it) }
    )
}

@Composable
private fun getSortingTypeTitle(sortingType: PasswordSortingType) =
    when (sortingType) {
        PasswordSortingType.Title -> stringResource(R.string.title)
        PasswordSortingType.RecentUsage -> stringResource(R.string.recent_usage)
        PasswordSortingType.FrequentUsage -> stringResource(R.string.frequent_usage)
        PasswordSortingType.CreationDate -> stringResource(R.string.creation_date)
        PasswordSortingType.EditingDate -> stringResource(R.string.editing_date)
    }

@Preview("Sorting dialog")
@Preview("Sorting dialog (ru)", locale = "ru")
@Preview("Sorting dialog (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SortingDialogPreview() {
    PasswordManagerTheme {
        Card {
            SortingDialogContent(
                sortingType = PasswordSortingType.FrequentUsage,
                onDismiss = {},
                onChangeSortingType = {},
            )
        }
    }
}