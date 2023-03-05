package ru.dimagor555.synchronization.usecase

import ru.dimagor555.synchronization.repository.ServerRepository

class StopServerUseCase(
    private val serverRepository: ServerRepository,
) {
    operator fun invoke() {
        serverRepository.stopServer()
    }
}
