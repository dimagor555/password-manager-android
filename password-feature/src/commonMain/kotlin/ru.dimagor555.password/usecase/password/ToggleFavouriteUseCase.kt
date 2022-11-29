package ru.dimagor555.password.usecase.password

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.repository.getByIdOrThrowException

class ToggleFavouriteUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(passwordId: String) = withContext(Dispatchers.Default) {
        val oldPassword = passwordRepository.getByIdOrThrowException(passwordId)
        val newPassword = oldPassword.toggledFavourite()
        passwordRepository.update(newPassword)
    }

    private fun Password.toggledFavourite() = copy(
        metadata = metadata.copy(isFavourite = !metadata.isFavourite)
    )
}