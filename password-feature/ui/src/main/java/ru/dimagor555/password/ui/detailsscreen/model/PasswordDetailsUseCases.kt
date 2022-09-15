package ru.dimagor555.password.ui.detailsscreen.model

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.*

internal class PasswordDetailsUseCases(
    passwordRepository: PasswordRepository,
    clipboardRepository: ClipboardRepository,
    decryptor: Decryptor
) {
    val observePassword = ObservePasswordUseCase(passwordRepository)

    val toggleFavourite = ToggleFavouriteUseCase(passwordRepository)

    val decryptPassword = DecryptPasswordUseCase(passwordRepository, decryptor)

    val copyPassword = CopyPasswordUseCase(passwordRepository, clipboardRepository, decryptor)
    val copyLogin = CopyLoginUseCase(passwordRepository, clipboardRepository)

    val removePassword = RemovePasswordUseCase(passwordRepository)
}