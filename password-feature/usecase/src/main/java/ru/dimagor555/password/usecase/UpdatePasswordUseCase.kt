package ru.dimagor555.password.usecase

import kotlinx.datetime.Clock
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.validation.PasswordValidation
import ru.dimagor555.password.repository.PasswordRepository

class UpdatePasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val encryptor: Encryptor
) {
    suspend operator fun invoke(params: Params) {
        validate(params)
        updatePassword(params)
    }

    private fun validate(params: Params) = with(params) {
        PasswordValidation(title, login, password).validate()
    }

    private suspend fun updatePassword(params: Params) {
        val oldPassword = passwordRepository.getById(params.id)
        val newPassword = oldPassword.createUpdated(params)
        passwordRepository.update(newPassword)
    }

    private fun Password.createUpdated(params: Params) = copy(
        title = params.title,
        login = params.login,
        encryptedPassword = encryptor.encrypt(params.password),
        editingDateTime = Clock.System.now()
    )

    data class Params(
        val id: Int,
        val title: String,
        val login: String,
        val password: String
    )
}
