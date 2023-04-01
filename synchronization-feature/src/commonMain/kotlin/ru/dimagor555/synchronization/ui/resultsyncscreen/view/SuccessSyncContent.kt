package ru.dimagor555.synchronization.ui.resultsyncscreen.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.res.core.MR
import ru.dimagor555.synchronization.domain.syncresult.SyncResult
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun SuccessSyncContent(syncResult: SyncResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SyncResultCard(stringResource(MR.strings.passwordsAdded), syncResult.addedPasswordsCount)
        SyncResultCard(stringResource(MR.strings.passwordsUpdated), syncResult.updatedPasswordsCount)
        SyncResultCard(stringResource(MR.strings.passwordsRemoved), syncResult.archivedPasswordsCount)
    }
}

@Composable
private fun SyncResultCard(
    syncResultType: String,
    itemsCount: Int,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = syncResultType,
            )
            Text(
                text = itemsCount.toString(),
            )
        }
    }
}
