package ru.dimagor555.password.usecase.field

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.metadata.plusUsage
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.password.repository.getByIdOrThrowException

abstract class CopyUseCase(
    private val passwordRepository: PasswordRepository,
    private val clipboardRepository: ClipboardRepository
) {

    suspend operator fun invoke(passwordId: String) = withContext(Dispatchers.Default) {
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