package ru.dimagor555.masterpassword.usecase.biometric.repository

internal interface BiometricSymmetricKeyRepository {

    suspend fun getOrNull(): String?

    suspend fun set(symmetricKey: String?)
}