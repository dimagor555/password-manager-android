package ru.dimagor555.encryption.symmetric.data

object SymmetricEncryptionProperties {
    const val ALGORITHM = "AES"
    const val KEY_SIZE_BITS = 256
    const val MODE = "GCM"
    const val PADDING = "NoPadding"
    internal const val TRANSFORMATION = "$ALGORITHM/$MODE/$PADDING"
    internal const val IV_SIZE_BYTES = 12
    internal const val TAG_SIZE_BITS = 128
}