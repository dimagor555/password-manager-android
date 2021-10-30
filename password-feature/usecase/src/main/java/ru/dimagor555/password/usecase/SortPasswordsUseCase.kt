package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.PasswordSorting

class SortPasswordsUseCase {
    operator fun invoke(passwords: List<Password>, passwordSorting: PasswordSorting) =
        passwords.sortedWith(passwordSorting.createComparator())
}