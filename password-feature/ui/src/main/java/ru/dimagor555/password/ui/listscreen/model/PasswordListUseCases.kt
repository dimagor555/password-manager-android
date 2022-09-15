package ru.dimagor555.password.ui.listscreen.model

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordFilterRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.*

internal class PasswordListUseCases(
    passwordRepository: PasswordRepository,
    passwordFilterRepository: PasswordFilterRepository,
    clipboardRepository: ClipboardRepository,
    decryptor: Decryptor
) {
    val observePasswords = ObservePasswordsUseCase(passwordRepository, passwordFilterRepository)

    val observePasswordFilterState = ObservePasswordFilterStateUseCase(passwordFilterRepository)
    val updatePasswordFilterState = UpdatePasswordFilterStateUseCase(passwordFilterRepository)

    val toggleFavourite = ToggleFavouriteUseCase(passwordRepository)
    val copyPassword = CopyPasswordUseCase(passwordRepository, clipboardRepository, decryptor)
}