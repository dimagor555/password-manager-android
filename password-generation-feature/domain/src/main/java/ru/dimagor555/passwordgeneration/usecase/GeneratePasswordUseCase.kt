package ru.dimagor555.passwordgeneration.usecase

import ru.dimagor555.passwordgeneration.domain.PasswordGenerationParams
import ru.dimagor555.passwordgeneration.domain.PasswordGenerator

class GeneratePasswordUseCase {
    operator fun invoke(params: PasswordGenerationParams) = PasswordGenerator(params).generate()
}