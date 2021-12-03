package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.PasswordFilter

class FilterPasswordsUseCase {
    suspend operator fun invoke(passwords: List<Password>, passwordFilter: PasswordFilter) =
        withContext(Dispatchers.Default) {
            passwords.filter { passwordFilter.matches(it) }
        }
}