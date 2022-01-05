package ru.dimagor555.password.commonedit.component

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.dimagor555.ui.core.component.button.SimpleIconButton
import ru.dimagor555.ui.core.component.topappbar.SimpleBackArrowTopAppBar
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

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