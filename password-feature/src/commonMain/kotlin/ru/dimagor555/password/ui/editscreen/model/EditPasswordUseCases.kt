package ru.dimagor555.password.ui.editscreen.model

import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildrenRepository
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.field.DecryptPasswordUseCase
import ru.dimagor555.password.usecase.password.single.GetPasswordUseCase
import ru.dimagor555.password.usecase.password.single.UpdatePasswordUseCase

internal class EditPasswordUseCases(
    passwordRepository: PasswordRepository,
    folderChildrenRepository: FolderChildrenRepository,
    encryptor: SymmetricEncryptor,
    decryptor: SymmetricDecryptor,
) {
    val getPassword = GetPasswordUseCase(passwordRepository)

    val decryptPassword = DecryptPasswordUseCase(passwordRepository, decryptor)

    val updatePassword = UpdatePasswordUseCase(passwordRepository, folderChildrenRepository, encryptor)
}