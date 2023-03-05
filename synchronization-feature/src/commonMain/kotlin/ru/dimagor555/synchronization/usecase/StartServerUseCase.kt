package ru.dimagor555.synchronization.usecase

import ru.dimagor555.synchronization.repository.ServerRepository

class StartServerUseCase(
    private val serverRepository: ServerRepository,
) {
    operator fun invoke() {
        serverRepository.createServer()
    }
}
