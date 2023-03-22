package ru.dimagor555.password.ui.editscreen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.button.SimpleTextButton
import ru.dimagor555.ui.core.util.stringResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SaveChangesDialog(
    onSave: () -> Unit,
    onDiscard: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = { Text(text = stringResource(MR.strings.save_changes_dialog_title)) },
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                SimpleTextButton(text = stringResource(MR.strings.cancel), onClick = onDismiss)
                SimpleTextButton(
                    text = stringResource(MR.strings.discard),
                    onClick = {
                        onDismiss()
                        onDiscard()
                    }
                )
                SimpleTextButton(
                    text = stringResource(MR.strings.save),
                    onClick = {
                        onDismiss()
                        onSave()
                    }
                )
            }
        }
    )
}