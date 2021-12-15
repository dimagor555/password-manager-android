package ru.dimagor555.ui.core.component.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.dimagor555.ui.core.R

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
        VisibilityOff
    else
        Visibility
}

private fun chooseContentDescriptionStringId(isVisible: Boolean) =
    if (isVisible)
        R.string.hide
    else
        R.string.show

@Preview("Visible content")
@Composable
private fun VisiblePreview() {
    VisibilityIconButton(isVisible = true, onToggleVisibility = {})
}

@Preview("Invisible content")
@Composable
private fun InvisiblePreview() {
    VisibilityIconButton(isVisible = false, onToggleVisibility = {})
}