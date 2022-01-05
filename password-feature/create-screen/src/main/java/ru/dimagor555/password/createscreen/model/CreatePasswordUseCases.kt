package ru.dimagor555.password.createscreen.model

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.CreatePasswordUseCase
import javax.inject.Inject

@ViewModelScoped
internal class CreatePasswordUseCases @Inject constructor(
    passwordRepository: PasswordRepository,
    encryptor: Encryptor
) {
    val createPassword = CreatePasswordUseCase(passwordRepository, encryptor)
}