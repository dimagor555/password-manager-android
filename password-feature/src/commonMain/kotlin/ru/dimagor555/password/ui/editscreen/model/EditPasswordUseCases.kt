package ru.dimagor555.password.ui.editscreen.model

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildrenRepository
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.field.DecryptPasswordUseCase
import ru.dimagor555.password.usecase.password.single.GetPasswordUseCase
import ru.dimagor555.password.usecase.password.single.UpdatePasswordUseCase

internal class EditPasswordUseCases(
    passwordRepository: PasswordRepository,
    folderChildrenRepository: FolderChildrenRepository,
    encryptor: Encryptor,
    decryptor: Decryptor,
) {
    val getPassword = GetPasswordUseCase(passwordRepository)

    val decryptPassword = DecryptPasswordUseCase(passwordRepository, decryptor)

    val updatePassword = UpdatePasswordUseCase(passwordRepository, folderChildrenRepository, encryptor)
}