package ru.dimagor555.password.listscreen.model

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordFilterRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.*
import javax.inject.Inject

@ViewModelScoped
internal class PasswordListUseCases @Inject constructor(
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