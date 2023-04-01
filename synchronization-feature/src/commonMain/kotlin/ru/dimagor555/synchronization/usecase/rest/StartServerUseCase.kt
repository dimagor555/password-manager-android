package ru.dimagor555.synchronization.usecase.rest

import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository

class StartServerUseCase(
    private val serverRepository: ServerRepository,
) {
    operator fun invoke() {
        serverRepository.createServer()
    }
}
