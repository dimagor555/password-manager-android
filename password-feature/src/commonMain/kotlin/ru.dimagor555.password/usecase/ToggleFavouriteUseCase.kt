package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.repository.getByIdOrThrowException

class ToggleFavouriteUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(passwordId: Int) = withContext(Dispatchers.Default) {
        val oldPassword = passwordRepository.getByIdOrThrowException(passwordId)
        val newPassword = oldPassword.toggledFavourite()
        passwordRepository.update(newPassword)
    }

    private fun Password.toggledFavourite() = copy(
        metadata = metadata.copy(isFavourite = !metadata.isFavourite)
    )
}