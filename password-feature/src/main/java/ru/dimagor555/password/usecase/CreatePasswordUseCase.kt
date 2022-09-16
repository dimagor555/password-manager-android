package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.validation.PasswordValidation
import ru.dimagor555.password.repository.PasswordRepository

class CreatePasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val encryptor: Encryptor
) {
    suspend operator fun invoke(params: Params) = withContext(Dispatchers.Default) {
        validate(params)
        val password = createPassword(params)
        passwordRepository.add(password)
    }

    private fun validate(params: Params) = with(params) {
        PasswordValidation(title, login, password).validate()
    }

    private fun createPassword(params: Params) = Password(
        title = params.title,
        login = params.login,
        encryptedPassword = encryptor.encrypt(params.password)
    )

    data class Params(
        val title: String,
        val login: String,
        val password: String
    )
}
