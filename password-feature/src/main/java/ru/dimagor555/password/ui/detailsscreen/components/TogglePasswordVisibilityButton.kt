package ru.dimagor555.password.ui.detailsscreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dimagor555.password.R
import ru.dimagor555.ui.core.component.button.SimpleButton

@Composable
internal fun TogglePasswordVisibilityButton(
    isVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    val btnText = stringResource(chooseButtonTextId(isVisible))
    SimpleButton(
        text = btnText,
        onClick = onToggleVisibility
    )
}

private fun chooseButtonTextId(isVisible: Boolean) =
    if (isVisible)
        R.string.hide_password
    else
        R.string.show_password