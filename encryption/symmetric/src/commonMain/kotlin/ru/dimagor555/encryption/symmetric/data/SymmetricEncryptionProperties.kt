package ru.dimagor555.encryption.symmetric.data

object SymmetricEncryptionProperties {
    internal const val ALGORITHM = "AES"
    private const val KEY_SIZE_BITS = 256
    private const val MODE = "GCM"
    private const val PADDING = "NoPadding"
    internal const val TRANSFORMATION = "$ALGORITHM/$MODE/$PADDING"
    internal const val IV_SIZE_BYTES = 12
    internal const val TAG_SIZE_BITS = 128
}