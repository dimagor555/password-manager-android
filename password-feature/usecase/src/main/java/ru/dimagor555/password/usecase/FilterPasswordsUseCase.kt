package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.PasswordFilter

class FilterPasswordsUseCase {
    operator fun invoke(passwords: List<Password>, passwordFilter: PasswordFilter) =
        passwords.filter { passwordFilter.matches(it) }
}