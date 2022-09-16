package ru.dimagor555.password.ui.detailsscreen.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dimagor555.password.R
import ru.dimagor555.ui.core.component.button.SimpleTextButton

@Composable
internal fun RemovePasswordDialog(
    onRemovePassword: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.remove_password_dialog_title))
        },
        text = {
            Text(text = stringResource(R.string.remove_password_dialog_text))
        },
        confirmButton = {
            SimpleTextButton(
                text = stringResource(R.string.remove),
                onClick = {
                    onDismiss()
                    onRemovePassword()
                }
            )
        },
        dismissButton = {
            SimpleTextButton(
                text = stringResource(R.string.cancel),
                onClick = onDismiss
            )
        }
    )
}