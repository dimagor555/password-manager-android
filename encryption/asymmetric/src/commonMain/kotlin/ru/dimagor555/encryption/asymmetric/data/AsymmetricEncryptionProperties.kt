package ru.dimagor555.encryption.asymmetric.data

object AsymmetricEncryptionProperties {
    internal const val ALGORITHM = "RSA"
    private const val MODE = "ECB"
    private const val PADDING = "OAEPWithSHA-256AndMGF1Padding"
    internal const val TRANSFORMATION = "$ALGORITHM/$MODE/$PADDING"
    internal const val KEY_SIZE_BITS = 4096
}