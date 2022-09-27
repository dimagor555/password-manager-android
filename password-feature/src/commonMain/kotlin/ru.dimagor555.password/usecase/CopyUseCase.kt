package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.plusUsage
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.repository.getByIdOrThrowException

abstract class CopyUseCase(
    private val passwordRepository: PasswordRepository,
    private val clipboardRepository: ClipboardRepository
) {
    suspend operator fun invoke(passwordId: Int) = withContext(Dispatchers.Default) {
        val password = passwordRepository.getByIdOrThrowException(passwordId)
        val textToCopy = getTextToCopy(password)
        setTextToClipboard(textToCopy)
        updateUsageHistory(password)
    }

    protected abstract fun getTextToCopy(password: Password): String

    private suspend fun setTextToClipboard(text: String) {
        clipboardRepository.setText(text)
    }

    private suspend fun updateUsageHistory(password: Password) {
        val newPassword = password.plusUsage()
        passwordRepository.update(newPassword)
    }

    private fun Password.plusUsage() = copy(
        metadata = metadata.copy(
            usageHistory = metadata.usageHistory.plusUsage()
        )
    )
}