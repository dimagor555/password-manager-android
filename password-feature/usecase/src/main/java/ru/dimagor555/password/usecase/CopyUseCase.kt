package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.Usage
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository

abstract class CopyUseCase(
    private val passwordRepository: PasswordRepository,
    private val clipboardRepository: ClipboardRepository
) {
    suspend operator fun invoke(password: Password) {
        val textToCopy = getTextToCopy(password)
        setTextToClipboard(textToCopy)
        addUsageToHistory(password)
    }

    protected abstract fun getTextToCopy(password: Password): String

    private fun setTextToClipboard(text: String) {
        clipboardRepository.setText(text)
    }

    private suspend fun addUsageToHistory(password: Password) {
        password.usageHistory.addUsage(Usage())
        passwordRepository.update(password)
    }
}