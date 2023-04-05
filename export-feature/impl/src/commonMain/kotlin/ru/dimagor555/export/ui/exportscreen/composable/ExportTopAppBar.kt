package ru.dimagor555.export.ui.exportscreen.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.topappbar.SimpleBackArrowTopAppBar
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun ExportTopAppBar(
    onNavigateBack: () -> Unit,
) {
    SimpleBackArrowTopAppBar(
        title = { Text(text = stringResource(MR.strings.export_title)) },
        onArrowBackClick = onNavigateBack,
    )
}