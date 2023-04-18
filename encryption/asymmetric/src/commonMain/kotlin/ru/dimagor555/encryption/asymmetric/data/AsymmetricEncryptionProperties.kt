package ru.dimagor555.encryption.asymmetric.data


//PKCS1Padding
//OAEPWithSHA-1AndMGF1Padding
//OAEPWithSHA-256AndMGF1Padding
object AsymmetricEncryptionProperties {
    internal const val ALGORITHM = "RSA"
    private const val MODE = "ECB"
    private const val PADDING = "OAEPWithSHA-1AndMGF1Padding"
    internal const val TRANSFORMATION = "$ALGORITHM/$MODE/$PADDING"
    internal const val KEY_SIZE_BITS = 4096
}