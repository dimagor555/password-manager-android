package ru.dimagor555.password.ui.detailsscreen.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.button.SimpleTextButton
import ru.dimagor555.ui.core.util.stringResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun RemovePasswordDialog(
    onRemovePassword: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(MR.strings.remove_password_dialog_title))
        },
        text = {
            Text(text = stringResource(MR.strings.remove_password_dialog_text))
        },
        confirmButton = {
            SimpleTextButton(
                text = stringResource(MR.strings.remove),
                onClick = {
                    onDismiss()
                    onRemovePassword()
                }
            )
        },
        dismissButton = {
            SimpleTextButton(
                text = stringResource(MR.strings.cancel),
                onClick = onDismiss
            )
        }
    )
}