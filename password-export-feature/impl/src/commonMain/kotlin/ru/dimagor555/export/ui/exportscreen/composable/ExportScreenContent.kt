package ru.dimagor555.export.ui.exportscreen.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.export.ui.exportscreen.store.ExportStore.Action
import ru.dimagor555.export.ui.exportscreen.store.ExportStore.State
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.AnimatedErrorTextBox
import ru.dimagor555.ui.core.component.CheckboxWithDescription
import ru.dimagor555.ui.core.component.MultiplatformContainer
import ru.dimagor555.ui.core.component.MultiplatformTopBottomLayout
import ru.dimagor555.ui.core.component.button.ThrottledLoadingButton
import ru.dimagor555.ui.core.component.textfield.SimpleErrorOutlinedTextField
import ru.dimagor555.ui.core.util.resolve
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun ExportScreenContent(
    state: State,
    sendAction: (Action) -> Unit,
) {
    MultiplatformContainer {
        MultiplatformTopBottomLayout(
            topContent = {
                ExportForm(
                    state = state,
                    sendAction = sendAction,
                )
            },
            bottomContent = {
                ExportButton(
                    state = state,
                    sendAction = sendAction,
                )
            }
        )
    }
}

@Composable
private fun ExportForm(
    state: State,
    sendAction: (Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = stringResource(MR.strings.export_file_name))
        SimpleErrorOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.fileName.text,
            onValueChange = { sendAction(Action.ChangeFileName(it)) },
            error = state.fileName.error?.resolve(),
            singleLine = true,
            enabled = state.isFileNameFieldEnabled,
        )
        CheckboxWithDescription(
            checked = state.isAddDateTimeToFileName,
            onClick = { sendAction(Action.ToggleAddDateTime) },
            description = stringResource(MR.strings.add_datetime_to_file_name),
        )
    }
}


@Composable
private fun ExportButton(
    state: State,
    sendAction: (Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AnimatedErrorTextBox(
            text = state.error?.resolve(),
            isVisible = state.error != null,
        )
        ThrottledLoadingButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(MR.strings.export_btn),
            onClick = { sendAction(Action.TryChooseFilePath) },
            contentPadding = PaddingValues(vertical = 16.dp),
            enabled = state.isExportEnabled,
            loading = state.isExportInProgress,
        )
    }
}