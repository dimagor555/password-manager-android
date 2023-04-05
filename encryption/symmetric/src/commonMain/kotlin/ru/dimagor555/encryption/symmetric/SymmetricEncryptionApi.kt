package ru.dimagor555.encryption.symmetric

import ru.dimagor555.encryption.symmetric.usecase.SetSymmetricKeyFromPasswordUsecase

interface SymmetricEncryptionApi {

    suspend fun setKeyFromPassword(password: String)
}

internal class SymmetricEncryptionApiImpl(
    private val setSymmetricKeyFromPassword: SetSymmetricKeyFromPasswordUsecase,
) : SymmetricEncryptionApi {

    override suspend fun setKeyFromPassword(password: String) {
        setSymmetricKeyFromPassword(password)
    }
}