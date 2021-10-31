package ru.dimagor555.password.data

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import ru.dimagor555.password.repository.ClipboardRepository

internal class ClipboardRepositoryImpl(
    private val applicationContext: Context
) : ClipboardRepository {
    override fun setText(text: String) {
        val clipboard = getClipboardManager() ?: return
        val clipData = createClipData(text)
        clipboard.setPrimaryClip(clipData)
    }

    private fun getClipboardManager() = applicationContext
        .getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager?

    private fun createClipData(text: String) = ClipData.newPlainText(PLAIN_TEXT_LABEL, text)

    companion object {
        private const val PLAIN_TEXT_LABEL = "plain text"
    }
}