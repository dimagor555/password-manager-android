package ru.dimagor555.password.listscreen

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.*
import javax.inject.Inject

@ViewModelScoped
internal class PasswordListUseCases @Inject constructor(
    passwordRepository: PasswordRepository,
    clipboardRepository: ClipboardRepository,
    decryptor: Decryptor
) {
    val observePasswords = ObservePasswordsUseCase(passwordRepository)

    val toggleFavourite = ToggleFavouriteUseCase(passwordRepository)
    val copyPassword = CopyPasswordUseCase(passwordRepository, clipboardRepository, decryptor)

    val filterPasswords = FilterPasswordsUseCase()
    val sortPasswords = SortPasswordsUseCase()
}