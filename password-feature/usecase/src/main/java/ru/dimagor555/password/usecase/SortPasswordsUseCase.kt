package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.PasswordSorting
import ru.dimagor555.password.domain.PasswordSortingType

class SortPasswordsUseCase {
    operator fun invoke(
        passwords: List<Password>,
        sortingType: PasswordSortingType
    ): List<Password> {
        val passwordSorting = PasswordSorting(sortingType)
        val comparator = passwordSorting.createComparator()
        return passwords.sortedWith(comparator)
    }
}