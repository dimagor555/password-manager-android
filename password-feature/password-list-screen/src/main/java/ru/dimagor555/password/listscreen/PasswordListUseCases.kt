package ru.dimagor555.password.listscreen

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.*

internal class PasswordListUseCases(
    passwordRepository: PasswordRepository,
    clipboardRepository: ClipboardRepository,
    decryptor: Decryptor
) {
    val observePasswords = ObservePasswordsUseCase(passwordRepository)

    val toggleFavourite = ToggleFavouriteUseCase(passwordRepository)
    val copyPassword = CopyPasswordUseCase(passwordRepository, clipboardRepository, decryptor)

    val filterPassword = FilterPasswordsUseCase()
    val sortPasswords = SortPasswordsUseCase()
}