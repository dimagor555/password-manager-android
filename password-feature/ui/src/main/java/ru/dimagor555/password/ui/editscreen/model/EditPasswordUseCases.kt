package ru.dimagor555.password.ui.editscreen.model

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.DecryptPasswordUseCase
import ru.dimagor555.password.usecase.GetPasswordUseCase
import ru.dimagor555.password.usecase.UpdatePasswordUseCase

internal class EditPasswordUseCases(
    passwordRepository: PasswordRepository,
    encryptor: Encryptor,
    decryptor: Decryptor
) {
    val getPassword = GetPasswordUseCase(passwordRepository)

    val decryptPassword = DecryptPasswordUseCase(passwordRepository, decryptor)

    val updatePassword = UpdatePasswordUseCase(passwordRepository, encryptor)
}