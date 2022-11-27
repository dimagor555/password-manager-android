package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.core.AlgorithmProperties.IV_SIZE_BYTES
import ru.dimagor555.encryption.domain.Base64

internal class EncryptedValue {
    private val iv: ByteArray
    private val bytes: ByteArray

    constructor(iv: ByteArray, bytes: ByteArray) {
        this.iv = iv
        this.bytes = bytes
    }

    constructor(encryptedValue: String, base64: Base64) {
        val encryptedValueBytes = base64.decodeFromString(encryptedValue)
        iv = encryptedValueBytes.copyOf(IV_SIZE_BYTES)
        bytes = encryptedValueBytes.drop(IV_SIZE_BYTES).toByteArray()
    }

    fun toBase64String(base64: Base64): String = base64.encodeToString(iv + bytes)

    operator fun component1() = iv
    operator fun component2() = bytes
}