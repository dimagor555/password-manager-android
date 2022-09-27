package ru.dimagor555.password.ui.detailsscreen.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.SingleLineText
import ru.dimagor555.ui.core.component.button.SimpleIconButton
import ru.dimagor555.ui.core.component.topappbar.DefaultTopAppBarDropdownMenu
import ru.dimagor555.ui.core.component.topappbar.SimpleBackArrowTopAppBar
import ru.dimagor555.ui.core.component.topappbar.SimpleDropdownMenuItem
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.stringResource

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
        contentDescription = stringResource(MR.strings.edit),
        onClick = navigateToPasswordEditingScreen
    )
}

@Composable
private fun Menu(
    onRemovePasswordClicked: () -> Unit
) {
    DefaultTopAppBarDropdownMenu { onDismiss ->
        SimpleDropdownMenuItem(
            text = stringResource(MR.strings.remove),
            onClick = onRemovePasswordClicked,
            onDismiss = onDismiss
        )
    }
}

@Preview
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