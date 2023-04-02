package ru.dimagor555.export.ui.importscreen.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.topappbar.SimpleBackArrowTopAppBar
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun ImportTopAppBar(
    onNavigateBack: () -> Unit,
) {
    SimpleBackArrowTopAppBar(
        title = { Text(text = stringResource(MR.strings.import_title)) },
        onArrowBackClick = onNavigateBack,
    )
}