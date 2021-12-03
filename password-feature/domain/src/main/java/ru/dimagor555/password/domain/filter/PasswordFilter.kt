package ru.dimagor555.password.domain.filter

import ru.dimagor555.password.domain.Password

class PasswordFilter(
    private val state: PasswordFilterState
) {
    fun filter(passwords: List<Password>) = passwords
        .filterPasswords()
        .sortPasswords()

    private fun List<Password>.filterPasswords() =
        PasswordFilterMatcher(state.searchText, state.favouriteFilter).filterPasswords(this)

    private fun List<Password>.sortPasswords() =
        PasswordSorter(state.sortingType).sortPassword(this)
}