package ru.dimagor555.password.usecase

import kotlinx.coroutines.flow.single
import kotlinx.datetime.Clock
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.validation.PasswordValidation
import ru.dimagor555.password.repository.PasswordRepository

class UpdatePasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val encryptor: Encryptor
) {
    suspend operator fun invoke(passwordDto: UpdatePasswordDto) {
        validate(passwordDto)
        updatePassword(passwordDto)
    }

    private fun validate(passwordDto: UpdatePasswordDto) = with(passwordDto) {
        PasswordValidation(title, login, password).validate()
    }

    private suspend fun updatePassword(passwordDto: UpdatePasswordDto) {
        val oldPassword = passwordRepository.getById(passwordDto.id).single()
        val newPassword = oldPassword.createUpdated(passwordDto)
        passwordRepository.update(newPassword)
    }

    private fun Password.createUpdated(passwordDto: UpdatePasswordDto) = copy(
        title = passwordDto.title,
        login = passwordDto.login,
        encryptedPassword = encryptor.encrypt(passwordDto.password),
        editingDateTime = Clock.System.now()
    )
}

data class UpdatePasswordDto(
    val id: Int,
    val title: String,
    val login: String,
    val password: String
)