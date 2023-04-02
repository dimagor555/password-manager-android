package ru.dimagor555.synchronization.ui.resultsyncscreen

interface ResultSyncComponent {

    data class ResultSyncComponentCallbacks(
        val navigateToSettingsScreen: () -> Unit,
    )
}