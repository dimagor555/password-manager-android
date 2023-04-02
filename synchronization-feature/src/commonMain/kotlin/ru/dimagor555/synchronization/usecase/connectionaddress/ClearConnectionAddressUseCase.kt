package ru.dimagor555.synchronization.usecase.connectionaddress

import ru.dimagor555.synchronization.data.connectionaddress.model.ConnectionAddress

class ClearConnectionAddressUseCase(
    private val connectionAddress: ConnectionAddress,
) {
    operator fun invoke() {
        connectionAddress.ip = null
    }
}