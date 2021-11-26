package ru.dimagor555.password.creationscreen

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.CreatePasswordUseCase
import javax.inject.Inject

@ViewModelScoped
internal class PasswordCreationUseCases @Inject constructor(
    passwordRepository: PasswordRepository,
    encryptor: Encryptor
) {
    val createPassword = CreatePasswordUseCase(passwordRepository, encryptor)
}