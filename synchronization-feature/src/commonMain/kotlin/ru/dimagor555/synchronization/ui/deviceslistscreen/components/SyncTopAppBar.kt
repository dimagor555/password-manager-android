package ru.dimagor555.synchronization.ui.deviceslistscreen.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ru.dimagor555.ui.core.component.topappbar.SimpleBackArrowTopAppBar
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview

@Composable
internal fun SyncTopAppBar(
    title: String,
    onNavigateBack: () -> Unit
) {
    SimpleBackArrowTopAppBar(
        title = { Text(text = title) },
        onArrowBackClick = onNavigateBack,
    )
}

@Preview
@Composable
private fun SyncTopAppBarPreview() {
    PasswordManagerTheme {
        SyncTopAppBar(
            title = "Synchronization",
            onNavigateBack = {}
        )
    }
}
