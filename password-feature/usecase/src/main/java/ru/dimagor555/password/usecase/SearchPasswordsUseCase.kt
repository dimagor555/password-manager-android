package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.Password

class SearchPasswordsUseCase {
    operator fun invoke(passwords: List<Password>, searchText: String) =
        passwords.filter { it.title.contains(searchText, ignoreCase = true) }
}