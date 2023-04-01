package ru.dimagor555.synchronization.ui.resultsyncscreen.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.res.core.MR
import ru.dimagor555.synchronization.domain.syncresult.SyncResult
import ru.dimagor555.ui.core.component.button.SimpleButton
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun ResultSyncScreenContent(
    isSyncSuccess: Boolean,
    syncResult: SyncResult,
    onOkButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        when (isSyncSuccess) {
            true -> SuccessSyncContent(syncResult)
            false -> NotSuccessSyncContent()
        }
        Spacer( modifier = Modifier.height(16.dp))
        SimpleButton(
            text = stringResource(MR.strings.ok),
            onClick = onOkButtonClick,
            modifier = Modifier,
        )
    }
}
