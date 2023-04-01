package ru.dimagor555.synchronization.usecase.connectionaddress

import ru.dimagor555.synchronization.data.connectionaddress.model.ConnectionAddress
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.SyncDeviceState

class SetConnectionAddressUseCase(
    private val connectionAddress: ConnectionAddress,
) {
    operator fun invoke(syncDeviceState: SyncDeviceState) {
        connectionAddress.ip = syncDeviceState.address
    }
}