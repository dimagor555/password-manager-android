package ru.dimagor555.password.domain

data class PasswordFilter(
    private val searchText: String,
    private val favouriteFilter: FavouriteFilter
) {
    fun matches(password: Password) =
        favouriteFilter.matches(password) && matchesSearchText(password)

    private fun matchesSearchText(password: Password) = with(password) {
        searchText.isBlank() ||
                title.contains(searchText, ignoreCase = true) ||
                login.contains(searchText, ignoreCase = true)
    }
}