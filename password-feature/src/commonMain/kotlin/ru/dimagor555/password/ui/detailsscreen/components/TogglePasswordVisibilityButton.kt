package ru.dimagor555.password.ui.detailsscreen.components

import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.button.SimpleButton
import ru.dimagor555.ui.core.util.stringResource

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
        MR.strings.hide_password
    else
        MR.strings.show_password