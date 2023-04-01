package ru.dimagor555.synchronization.ui.syncscreen

interface SyncComponent {

    data class SyncComponentCallbacks(
        val navigateBack: () -> Unit,
        val navigateToResultSyncScreen: (isSuccess: Boolean) -> Unit,
    )
}