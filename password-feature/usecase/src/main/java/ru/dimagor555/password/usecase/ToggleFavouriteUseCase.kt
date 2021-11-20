package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.PasswordRepository

class ToggleFavouriteUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(passwordId: Int) {
        val oldPassword = passwordRepository.getById(passwordId)
        val newPassword = oldPassword.toggledFavourite()
        passwordRepository.update(newPassword)
    }

    private fun Password.toggledFavourite() = copy(isFavourite = !isFavourite)
}