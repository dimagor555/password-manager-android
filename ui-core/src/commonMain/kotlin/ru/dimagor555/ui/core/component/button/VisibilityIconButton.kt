package ru.dimagor555.ui.core.component.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import androidx.compose.desktop.ui.tooling.preview.Preview
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun VisibilityIconButton(
    isVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    SimpleIconButton(
        icon = chooseVisibilityIcon(isVisible),
        contentDescription = stringResource(chooseContentDescriptionStringId(isVisible)),
        onClick = onToggleVisibility
    )
}

private fun chooseVisibilityIcon(isVisible: Boolean) = Icons.Default.run {
    if (isVisible)
        Icons.Default.VisibilityOff
    else
        Icons.Default.Visibility
}

private fun chooseContentDescriptionStringId(isVisible: Boolean) =
    if (isVisible)
        MR.strings.hide
    else
        MR.strings.show

@Preview
@Composable
private fun VisiblePreview() {
    VisibilityIconButton(isVisible = true, onToggleVisibility = {})
}

@Preview
@Composable
private fun InvisiblePreview() {
    VisibilityIconButton(isVisible = false, onToggleVisibility = {})
}