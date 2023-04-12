package ru.dimagor555.encryption.symmetric

import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import ru.dimagor555.encryption.symmetric.usecase.SetSymmetricKeyFromPasswordUsecase

interface SymmetricEncryptionApi {

    suspend fun setKeyFromPassword(password: String)

    fun setKey(symmetricKey: SymmetricKey)

    fun getKey(): SymmetricKey
}

internal class SymmetricEncryptionApiImpl(
    private val setSymmetricKeyFromPassword: SetSymmetricKeyFromPasswordUsecase,
    private val symmetricKeyRepository: SymmetricKeyRepository,
) : SymmetricEncryptionApi {

    override suspend fun setKeyFromPassword(password: String) =
        setSymmetricKeyFromPassword(password)

    override fun setKey(symmetricKey: SymmetricKey) =
        symmetricKeyRepository.set(symmetricKey)

    override fun getKey(): SymmetricKey =
        symmetricKeyRepository.get()
}