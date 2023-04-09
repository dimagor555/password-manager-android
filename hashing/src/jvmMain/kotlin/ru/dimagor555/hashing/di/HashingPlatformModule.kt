package ru.dimagor555.hashing.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.hashing.data.Argon2PasswordHasher
import ru.dimagor555.hashing.domain.PasswordHasher

actual val hashingPlatformModule: Module = module {
    factoryOf(::Argon2PasswordHasher) bind PasswordHasher::class
}