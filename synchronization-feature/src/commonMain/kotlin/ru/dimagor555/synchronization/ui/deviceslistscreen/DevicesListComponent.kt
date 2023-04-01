package ru.dimagor555.synchronization.ui.deviceslistscreen

interface DevicesListComponent {

    data class DevicesListComponentCallbacks(
        val navigateBack: () -> Unit,
        val navigateToSyncScreen: (isClient: Boolean) -> Unit,
    )
}