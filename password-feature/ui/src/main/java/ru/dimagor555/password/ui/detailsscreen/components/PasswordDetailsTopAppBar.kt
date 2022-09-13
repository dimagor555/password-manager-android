package ru.dimagor555.password.ui.detailsscreen.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.dimagor555.password.ui.R
import ru.dimagor555.ui.core.component.*
import ru.dimagor555.ui.core.component.button.SimpleIconButton
import ru.dimagor555.ui.core.component.topappbar.DefaultTopAppBarDropdownMenu
import ru.dimagor555.ui.core.component.topappbar.SimpleBackArrowTopAppBar
import ru.dimagor555.ui.core.component.topappbar.SimpleDropdownMenuItem
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun PasswordDetailsTopAppBar(
    title: String,
    onRemovePasswordClicked: () -> Unit,
    navigateBack: () -> Unit,
    navigateToPasswordEditingScreen: () -> Unit
) {
    SimpleBackArrowTopAppBar(
        title = { TitleText(text = title) },
        onArrowBackClick = navigateBack,
        actions = {
            EditIconButton(navigateToPasswordEditingScreen = navigateToPasswordEditingScreen)
            Menu(onRemovePasswordClicked = onRemovePasswordClicked)
        }
    )
}

@Composable
private fun TitleText(text: String) {
    SingleLineText(
        text = text,
        style = MaterialTheme.typography.h5
    )
}

@Composable
private fun EditIconButton(
    navigateToPasswordEditingScreen: () -> Unit
) {
    SimpleIconButton(
        icon = Icons.Default.Edit,
        contentDescription = stringResource(R.string.edit),
        onClick = navigateToPasswordEditingScreen
    )
}

@Composable
private fun Menu(
    onRemovePasswordClicked: () -> Unit
) {
    DefaultTopAppBarDropdownMenu { onDismiss ->
        SimpleDropdownMenuItem(
            text = stringResource(R.string.remove),
            onClick = onRemovePasswordClicked,
            onDismiss = onDismiss
        )
    }
}

@Preview("Password details TopAppBar")
@Preview("Password details TopAppBar (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PasswordDetailsTopAppBarPreview() {
    PasswordManagerTheme {
        PasswordDetailsTopAppBar(
            title = "Google",
            onRemovePasswordClicked = {},
            navigateBack = {},
            navigateToPasswordEditingScreen = {}
        )
    }
}