package ru.dimagor555.password.ui.commoneditscreen.component

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import ru.dimagor555.ui.core.component.button.SimpleIconButton
import ru.dimagor555.ui.core.component.topappbar.SimpleBackArrowTopAppBar
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import androidx.compose.desktop.ui.tooling.preview.Preview

@Composable
internal fun CommonEditPasswordTopAppBar(
    title: String,
    onValidate: () -> Unit,
    onNavigateBack: () -> Unit
) {
    SimpleBackArrowTopAppBar(
        title = { Text(text = title) },
        onArrowBackClick = onNavigateBack,
        actions = {
            SimpleIconButton(
                icon = Icons.Default.Check,
                contentDescription = null,
                onClick = onValidate
            )
        }
    )
}

@Preview
@Composable
private fun PasswordEditingTopAppBarPreview() {
    PasswordManagerTheme {
        CommonEditPasswordTopAppBar(
            title = "Create",
            onValidate = {},
            onNavigateBack = {}
        )
    }
}