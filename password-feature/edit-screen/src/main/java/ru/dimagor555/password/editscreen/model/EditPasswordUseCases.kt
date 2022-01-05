package ru.dimagor555.password.editscreen.model

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.DecryptPasswordUseCase
import ru.dimagor555.password.usecase.GetPasswordUseCase
import ru.dimagor555.password.usecase.UpdatePasswordUseCase
import javax.inject.Inject

@ViewModelScoped
internal class EditPasswordUseCases @Inject constructor(
    passwordRepository: PasswordRepository,
    encryptor: Encryptor,
    decryptor: Decryptor
) {
    val getPassword = GetPasswordUseCase(passwordRepository)

    val decryptPassword = DecryptPasswordUseCase(passwordRepository, decryptor)

    val updatePassword = UpdatePasswordUseCase(passwordRepository, encryptor)
}