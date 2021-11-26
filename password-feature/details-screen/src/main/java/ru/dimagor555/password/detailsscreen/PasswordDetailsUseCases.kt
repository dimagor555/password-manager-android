package ru.dimagor555.password.detailsscreen

import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.*
import javax.inject.Inject

@ViewModelScoped
internal class PasswordDetailsUseCases @Inject constructor(
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