package ru.dimagor555.encryption.android

import ru.dimagor555.encryption.domain.Base64
import android.util.Base64 as AndroidUtilBase64

internal class AndroidBase64 : Base64 {
    override fun encodeToString(input: ByteArray): String =
        AndroidUtilBase64.encodeToString(input, AndroidUtilBase64.NO_WRAP)

    override fun decodeFromString(input: String): ByteArray =
        AndroidUtilBase64.decode(input, AndroidUtilBase64.NO_WRAP)
}