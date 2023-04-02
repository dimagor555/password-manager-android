package ru.dimagor555.export.ui.importscreen.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.dimagor555.export.ui.importscreen.store.ImportStore.Action
import ru.dimagor555.export.ui.importscreen.store.ImportStore.State
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.AnimatedErrorTextBox
import ru.dimagor555.ui.core.component.MultiplatformTopBottomLayout
import ru.dimagor555.ui.core.component.CheckboxWithDescription
import ru.dimagor555.ui.core.component.MultiplatformContainer
import ru.dimagor555.ui.core.component.button.SimpleTextButton
import ru.dimagor555.ui.core.component.button.ThrottledLoadingButton
import ru.dimagor555.ui.core.util.rememberThrottledFalseBoolean
import ru.dimagor555.ui.core.util.resolve
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun ImportScreenContent(
    state: State,
    sendAction: (Action) -> Unit,
) {
    MultiplatformContainer {
        MultiplatformTopBottomLayout(
            topContent = {
                ImportForm(
                    state = state,
                    sendAction = sendAction,
                )
            },
            bottomContent = {
                ImportButton(
                    state = state,
                    sendAction = sendAction,
                )
            }
        )
    }
}

@Composable
private fun ImportForm(
    state: State,
    sendAction: (Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = stringResource(MR.strings.choose_import_file))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.selectedFileText.resolve(),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
        SimpleTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(MR.strings.choose_file_btn),
            onClick = { sendAction(Action.TryChooseFile) },
            contentPadding = PaddingValues(vertical = 16.dp),
            enabled = rememberThrottledFalseBoolean(state.isFileChoosingEnabled),
        )
        CheckboxWithDescription(
            checked = state.isClearBeforeImport,
            onClick = { sendAction(Action.ToggleClearBeforeImport) },
            description = stringResource(MR.strings.clear_before_import),
        )
        CheckboxWithDescription(
            checked = state.isMakeBackup,
            onClick = { sendAction(Action.ToggleMakeBackup) },
            description = stringResource(MR.strings.make_backup),
        )
    }
}

@Composable
private fun ImportButton(
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
            text = stringResource(MR.strings.import_btn),
            onClick = { sendAction(Action.TryImport) },
            contentPadding = PaddingValues(vertical = 16.dp),
            enabled = state.isImportEnabled,
            loading = state.isImportInProgress,
        )
    }
}