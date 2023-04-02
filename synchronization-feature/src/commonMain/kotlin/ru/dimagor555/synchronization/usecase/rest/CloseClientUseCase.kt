package ru.dimagor555.synchronization.usecase.rest

import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository

class CloseClientUseCase(
    private val clientRepository: ClientRepository,
) {
    operator fun invoke() = clientRepository.closeClient()
}