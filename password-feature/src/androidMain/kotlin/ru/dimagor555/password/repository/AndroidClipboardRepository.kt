package ru.dimagor555.password.repository

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidClipboardRepository(private val applicationContext: Context): ClipboardRepository {

    override suspend fun setText(text: String) = withContext(Dispatchers.Main) {
        val clipboard = getClipboardManager() ?: return@withContext
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
