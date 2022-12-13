package ru.dimagor555.password.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class JvmClipboardRepository: ClipboardRepository {

    override suspend fun setText(text: String) = withContext(Dispatchers.Main) {
        val clipboard = getClipboard() ?: return@withContext
        clipboard.setContents(StringSelection(text), null)
    }

    private fun getClipboard() = Toolkit.getDefaultToolkit().systemClipboard
}
