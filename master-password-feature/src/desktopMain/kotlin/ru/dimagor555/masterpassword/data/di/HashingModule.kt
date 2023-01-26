package ru.dimagor555.masterpassword.data.di

import org.koin.dsl.module
import ru.dimagor555.masterpassword.data.Argon2Hasher
import ru.dimagor555.masterpassword.domain.Hasher

actual val hashingModule = module {
    factory<Hasher> { Argon2Hasher() }
}