package ru.dimagor555.synchronization.ui.syncscreen.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.res.core.MR
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore.Action
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore.State
import ru.dimagor555.ui.core.component.SingleLineText
import ru.dimagor555.ui.core.component.button.SimpleButton
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun SyncScreenContent(
    state: State,
    sendAction: (Action) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        DeterminateProgressBar()
        Spacer(modifier = Modifier.height(16.dp))
        SingleLineText(
            text = state.syncStatus.format(),
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SimpleButton(
            text = stringResource(MR.strings.cancel),
            onClick = { sendAction(Action.StopSync) },
            modifier = Modifier,
            enabled = state.canStopped,
        )
    }
}

//TODO move to SyncScreenActor
@Composable
private fun SyncStatus.format(): String =
    when (this) {
        is SyncStatus.Initial -> stringResource(MR.strings.synchronization)
        is SyncStatus.SendingPasswords -> stringResource(MR.strings.sendingPasswords)
        is SyncStatus.ReceivedPasswordsAnalysis -> stringResource(MR.strings.receivedPasswordsAnalysis)
        is SyncStatus.SavingReceivedPasswords -> stringResource(MR.strings.savingReceivedPasswords)
        is SyncStatus.SuccessSync -> stringResource(MR.strings.successSync)
        is SyncStatus.ErrorSync -> stringResource(MR.strings.errorSync)
    }
