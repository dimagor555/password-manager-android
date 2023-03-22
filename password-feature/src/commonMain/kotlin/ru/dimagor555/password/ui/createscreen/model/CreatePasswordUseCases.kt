package ru.dimagor555.password.ui.createscreen.model

import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.repository.FolderChildrenRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.password.CreatePasswordUseCase

internal class CreatePasswordUseCases(
    passwordRepository: PasswordRepository,
    folderChildrenRepository: FolderChildrenRepository,
    encryptor: Encryptor,
) {
    val createPassword =
        CreatePasswordUseCase(passwordRepository, folderChildrenRepository, encryptor)
}