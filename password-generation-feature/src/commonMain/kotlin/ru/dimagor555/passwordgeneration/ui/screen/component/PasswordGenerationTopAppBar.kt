package ru.dimagor555.passwordgeneration.ui.screen.component

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.button.SimpleIconButton
import ru.dimagor555.ui.core.component.topappbar.SimpleBackArrowTopAppBar
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun PasswordGenerationTopAppBar(
    onNavigateBackWithPassword: () -> Unit,
    onNavigateBack: () -> Unit
) {
    SimpleBackArrowTopAppBar(
        title = { Text(text = stringResource(MR.strings.generate)) },
        onArrowBackClick = onNavigateBack,
        actions = {
            SimpleIconButton(
                icon = Icons.Default.Check,
                contentDescription = null,
                onClick = onNavigateBackWithPassword
            )
        }
    )
}

@Preview
@Composable
private fun PasswordGenerationTopAppBarPreview() {
    PasswordManagerTheme {
        PasswordGenerationTopAppBar({}, {})
    }
}