package ru.dimagor555.masterpassword.hashing.di

import com.lambdapioneer.argon2kt.Argon2Kt
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.domain.Hasher
import ru.dimagor555.masterpassword.hashing.Argon2Hasher

val hashingModule = module {
    single { Argon2Kt() }
    factoryOf(::Argon2Hasher) bind Hasher::class
}