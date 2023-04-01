package ru.dimagor555.synchronization.ui.deviceslistscreen.store

import ru.dimagor555.synchronization.domain.syncdevice.SyncDevice

data class SyncDeviceState(
    val hostName: String = "",
    val address: String = "",
)

fun SyncDevice.toSyncDeviceState() = SyncDeviceState(
    hostName = hostName,
    address = address,
)

fun List<SyncDevice>.toSyncDeviceStates() = map {
    it.toSyncDeviceState()
}
