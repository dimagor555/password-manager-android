package ru.dimagor555.password.detailsscreen.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.dimagor555.password.detailsscreen.R
import ru.dimagor555.ui.core.components.*
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun PasswordDetailsTopAppBar(
    title: String,
    onRemovePasswordClicked: () -> Unit,
    navigateBack: () -> Unit,
    navigateToPasswordEditingScreen: () -> Unit
) {
    TopAppBar(
        title = { TitleText(text = title) },
        navigationIcon = { ArrowBackIconButton(onClick = navigateBack) },
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
    DefaultTopAppBarDropdownMenu {
        SimpleDropdownMenuItem(
            text = stringResource(R.string.remove),
            onClick = onRemovePasswordClicked
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