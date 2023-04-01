package ru.dimagor555.synchronization.usecase.rest

import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository

class StopServerUseCase(
    private val serverRepository: ServerRepository,
) {
    operator fun invoke() {
        serverRepository.stopServer()
    }
}
