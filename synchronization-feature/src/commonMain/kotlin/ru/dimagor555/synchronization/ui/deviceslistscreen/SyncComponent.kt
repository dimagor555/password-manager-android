package ru.dimagor555.synchronization.ui.deviceslistscreen

interface SyncComponent {

    data class SyncComponentCallbacks(
        val navigateToSettingsScreen: () -> Unit,
    )
}