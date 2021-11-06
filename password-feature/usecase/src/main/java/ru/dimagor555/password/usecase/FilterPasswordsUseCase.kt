package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.Password

class FilterPasswordsUseCase {
    operator fun invoke(passwords: List<Password>, filterCondition: String) =
        passwords.filter { it.matches(filterCondition) }

    private fun Password.matches(filterCondition: String) =
        title.contains(filterCondition, ignoreCase = true) ||
                login.contains(filterCondition, ignoreCase = true)
}