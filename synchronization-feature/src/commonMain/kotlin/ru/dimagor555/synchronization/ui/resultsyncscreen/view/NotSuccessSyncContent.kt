package ru.dimagor555.synchronization.ui.resultsyncscreen.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun NotSuccessSyncContent() {
    Text(
        text = stringResource(MR.strings.error_synchronization),
        style = MaterialTheme.typography.h3,
    )
}
