package ru.dimagor555.password.domain.filter

import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.Password

internal class PasswordFilterMatcher(
    searchText: String,
    private val favouriteFilter: FavouriteFilter
) {
    private val searchText = searchText.trim()

    fun filterPasswords(passwords: List<Password>) = passwords.filter { matches(it) }

    private fun matches(password: Password) =
        favouriteFilter.matches(password) && matchesSearchText(password)

    private fun matchesSearchText(password: Password) = with(password) {
        searchText.isBlank() ||
                title.contains(searchText, ignoreCase = true) ||
                login.contains(searchText, ignoreCase = true)
    }
}