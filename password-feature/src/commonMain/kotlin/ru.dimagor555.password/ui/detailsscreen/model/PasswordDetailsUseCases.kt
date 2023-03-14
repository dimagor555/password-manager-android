package ru.dimagor555.password.ui.detailsscreen.model

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.FolderChildrenRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.field.CopyLoginUseCase
import ru.dimagor555.password.usecase.field.CopyPasswordUseCase
import ru.dimagor555.password.usecase.field.DecryptPasswordUseCase
import ru.dimagor555.password.usecase.password.ObservePasswordUseCase
import ru.dimagor555.password.usecase.password.ToggleFavouriteUseCase
import ru.dimagor555.password.usecase.password.UpdatePasswordUseCase

internal class PasswordDetailsUseCases(
    passwordRepository: PasswordRepository,
    folderChildrenRepository: FolderChildrenRepository,
    clipboardRepository: ClipboardRepository,
    decryptor: Decryptor,
    encryptor: Encryptor,
) {
    val observePassword = ObservePasswordUseCase(passwordRepository)

    val toggleFavourite = ToggleFavouriteUseCase(passwordRepository)

    val decryptPassword = DecryptPasswordUseCase(passwordRepository, decryptor)

    val copyPassword = CopyPasswordUseCase(passwordRepository, clipboardRepository, decryptor)
    val copyLogin = CopyLoginUseCase(passwordRepository, clipboardRepository)

    val updatePassword = UpdatePasswordUseCase(passwordRepository, folderChildrenRepository, encryptor)
}
