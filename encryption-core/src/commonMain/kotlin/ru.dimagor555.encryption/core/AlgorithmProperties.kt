package ru.dimagor555.encryption.core

object AlgorithmProperties {
    const val AES_ALGORITHM = "AES"
    const val AES_KEY_SIZE_BITS = 256
    private const val AES_MODE = "GCM"
    private const val AES_PADDING = "NoPadding"
    internal const val TRANSFORMATION = "$AES_ALGORITHM/$AES_MODE/$AES_PADDING"
    internal const val IV_SIZE_BYTES = 12
    internal const val TAG_SIZE_BITS = 128
}