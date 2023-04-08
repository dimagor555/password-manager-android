package ru.dimagor555.encryption.symmetric.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module
import ru.dimagor555.encryption.symmetric.SymmetricEncryptionApi
import ru.dimagor555.encryption.symmetric.SymmetricEncryptionApiImpl
import ru.dimagor555.encryption.symmetric.data.aes.AesCryptor
import ru.dimagor555.encryption.symmetric.data.hasher.SHA256Hasher
import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import ru.dimagor555.encryption.symmetric.usecase.SetSymmetricKeyFromPasswordUsecase

val symmetricEncryptionModule = module {
    factoryOf(::SymmetricEncryptionApiImpl) bind SymmetricEncryptionApi::class

    singleOf(::SymmetricKeyRepository)
    factoryOf(::SHA256Hasher)

    factory { params ->
        val customSymmetricKey = params.values.firstOrNull() as? SymmetricKey
        AesCryptor(
            customSymmetricKey = customSymmetricKey,
            symmetricKeyRepository = get(),
        )
    } binds arrayOf(SymmetricEncryptor::class, SymmetricDecryptor::class)

    factoryOf(::SetSymmetricKeyFromPasswordUsecase)
}