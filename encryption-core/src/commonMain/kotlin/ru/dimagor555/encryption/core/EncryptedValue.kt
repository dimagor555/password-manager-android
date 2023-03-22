package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.core.AlgorithmProperties.IV_SIZE_BYTES
import saschpe.kase64.base64DecodedBytes
import saschpe.kase64.base64Encoded

internal class EncryptedValue {
    private val iv: ByteArray
    private val bytes: ByteArray

    constructor(iv: ByteArray, bytes: ByteArray) {
        this.iv = iv
        this.bytes = bytes
    }

    constructor(encryptedValue: String) {
        val encryptedValueBytes = encryptedValue.base64DecodedBytes
        iv = encryptedValueBytes.copyOf(IV_SIZE_BYTES)
        bytes = encryptedValueBytes.drop(IV_SIZE_BYTES).toByteArray()
    }

    fun toBase64String(): String = (iv + bytes).base64Encoded

    operator fun component1() = iv
    operator fun component2() = bytes
}