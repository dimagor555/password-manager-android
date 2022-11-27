package ru.dimagor555.password.ui.listscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.domain.filter.PasswordSortingType
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.DefaultDialog
import ru.dimagor555.ui.core.component.SingleLineText
import ru.dimagor555.ui.core.component.button.RadioTextOptionGroup
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.stringResource

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
            text = stringResource(MR.strings.sort_by),
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
        PasswordSortingType.Title -> stringResource(MR.strings.title)
        PasswordSortingType.RecentUsage -> stringResource(MR.strings.recent_usage)
        PasswordSortingType.FrequentUsage -> stringResource(MR.strings.frequent_usage)
        PasswordSortingType.CreationDate -> stringResource(MR.strings.creation_date)
        PasswordSortingType.EditingDate -> stringResource(MR.strings.editing_date)
    }

@Preview
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