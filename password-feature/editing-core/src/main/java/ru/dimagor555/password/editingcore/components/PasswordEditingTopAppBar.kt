package ru.dimagor555.password.editingcore.components

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.dimagor555.ui.core.components.SimpleBackArrowTopAppBar
import ru.dimagor555.ui.core.components.SimpleIconButton
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun PasswordEditingTopAppBar(
    title: String,
    onTryFinishEditing: () -> Unit,
    onNavigateBack: () -> Unit
) {
    SimpleBackArrowTopAppBar(
        title = { Text(text = title) },
        onArrowBackClick = onNavigateBack,
        actions = {
            SimpleIconButton(
                icon = Icons.Default.Check,
                contentDescription = null,
                onClick = onTryFinishEditing
            )
        }
    )
}

@Preview
@Composable
private fun PasswordEditingTopAppBarPreview() {
    PasswordManagerTheme {
        PasswordEditingTopAppBar(
            title = "Create",
            onTryFinishEditing = {},
            onNavigateBack = {}
        )
    }
}