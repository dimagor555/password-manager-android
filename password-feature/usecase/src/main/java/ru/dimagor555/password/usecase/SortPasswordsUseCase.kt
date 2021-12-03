package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.PasswordSorting
import ru.dimagor555.password.domain.PasswordSortingType

class SortPasswordsUseCase {
    suspend operator fun invoke(
        passwords: List<Password>,
        sortingType: PasswordSortingType
    ): List<Password> = withContext(Dispatchers.Default) {
        val passwordSorting = PasswordSorting(sortingType)
        val comparator = passwordSorting.createComparator()
        passwords.sortedWith(comparator)
    }
}