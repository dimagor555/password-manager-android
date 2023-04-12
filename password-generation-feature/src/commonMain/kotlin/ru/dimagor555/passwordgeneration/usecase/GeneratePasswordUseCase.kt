package ru.dimagor555.passwordgeneration.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.passwordgeneration.domain.PasswordGenerationParams
import ru.dimagor555.passwordgeneration.domain.PasswordGenerator

class GeneratePasswordUseCase {
    suspend operator fun invoke(params: PasswordGenerationParams) =
        withContext(Dispatchers.Default) {
            PasswordGenerator(params).generate()
        }
}