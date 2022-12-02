package ru.dimagor555.password.ui.listscreen.model

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.FilterRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.field.CopyPasswordUseCase
import ru.dimagor555.password.usecase.filter.ObserveFilterStateUseCase
import ru.dimagor555.password.usecase.filter.UpdateFilterStateUseCase
import ru.dimagor555.password.usecase.password.ObservePasswordsUseCase
import ru.dimagor555.password.usecase.password.ToggleFavouriteUseCase

internal class PasswordListUseCases(
    passwordRepository: PasswordRepository,
    filterRepository: FilterRepository,
    clipboardRepository: ClipboardRepository,
    decryptor: Decryptor,
) {
    val observePasswords = ObservePasswordsUseCase(passwordRepository, filterRepository)

    val observePasswordFilterState = ObserveFilterStateUseCase(filterRepository)
    val updatePasswordFilterState = UpdateFilterStateUseCase(filterRepository)

    val toggleFavourite = ToggleFavouriteUseCase(passwordRepository)
    val copyPassword = CopyPasswordUseCase(passwordRepository, clipboardRepository, decryptor)
}