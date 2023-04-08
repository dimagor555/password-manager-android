package ru.dimagor555.encryption.symmetric

import ru.dimagor555.encryption.symmetric.usecase.GetSymmetricKeyBase64Usecase
import ru.dimagor555.encryption.symmetric.usecase.SetSymmetricKeyFromBase64Usecase
import ru.dimagor555.encryption.symmetric.usecase.SetSymmetricKeyFromPasswordUsecase

interface SymmetricEncryptionApi {

    suspend fun setKeyFromPassword(password: String)

    fun setKeyFromBase64(encodedKey: String)

    fun getKeyBase64(): String
}

internal class SymmetricEncryptionApiImpl(
    private val setSymmetricKeyFromPassword: SetSymmetricKeyFromPasswordUsecase,
    private val setSymmetricKeyFromBase64: SetSymmetricKeyFromBase64Usecase,
    private val getSymmetricKeyBase64: GetSymmetricKeyBase64Usecase,
) : SymmetricEncryptionApi {

    override suspend fun setKeyFromPassword(password: String) =
        setSymmetricKeyFromPassword(password)

    override fun setKeyFromBase64(encodedKey: String) =
        setSymmetricKeyFromBase64(encodedKey)

    override fun getKeyBase64(): String =
        getSymmetricKeyBase64()
}