package ru.dimagor555.password.usecase

import kotlinx.coroutines.flow.first
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.PasswordRepository

class ToggleFavouriteUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(passwordId: Int) {
        val oldPassword = getPasswordById(passwordId)
        val newPassword = oldPassword.toggledFavourite()
        passwordRepository.update(newPassword)
    }

    private suspend fun getPasswordById(passwordId: Int) =
        passwordRepository.getById(passwordId).first()

    private fun Password.toggledFavourite() = copy(isFavourite = !isFavourite)
}