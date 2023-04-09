package ru.dimagor555.hashing.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.hashing.data.Sha256HasherImpl
import ru.dimagor555.hashing.domain.Argon2SymmetricKeySaltGenerator
import ru.dimagor555.hashing.domain.Sha256Hasher

val hashingModule = module {
    includes(hashingPlatformModule)

    factoryOf(::Sha256HasherImpl) bind Sha256Hasher::class
    factoryOf(::Argon2SymmetricKeySaltGenerator)
}

expect val hashingPlatformModule: Module