package ru.dimagor555.password.editingscreen.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dimagor555.password.editingscreen.R
import ru.dimagor555.ui.core.components.SimpleTextButton

@Composable
internal fun SaveChangesDialog(
    onSave: () -> Unit,
    onDiscard: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.save_changes_dialog_title)) },
        buttons = {
            SimpleTextButton(text = stringResource(R.string.cancel), onClick = onDismiss)
            SimpleTextButton(text = stringResource(R.string.discard), onClick = onDiscard)
            SimpleTextButton(text = stringResource(R.string.save), onClick = onSave)
        }
    )
}