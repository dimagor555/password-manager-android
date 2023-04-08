package ru.dimagor555.encryption.asymmetric.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.encryption.asymmetric.data.rsa.RsaDecryptor
import ru.dimagor555.encryption.asymmetric.data.rsa.RsaEncryptor
import ru.dimagor555.encryption.asymmetric.domain.AsymmetricDecryptor
import ru.dimagor555.encryption.asymmetric.domain.AsymmetricEncryptor

val asymmetricEncryptionModule = module {
    factoryOf(::RsaEncryptor) bind AsymmetricEncryptor::class
    factoryOf(::RsaDecryptor) bind AsymmetricDecryptor::class
}