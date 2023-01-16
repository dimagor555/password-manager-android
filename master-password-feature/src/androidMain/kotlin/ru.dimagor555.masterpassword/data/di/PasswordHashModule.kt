package ru.dimagor555.masterpassword.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.data.PrefsPasswordHashDao
import ru.dimagor555.masterpassword.domain.PasswordHashDao

actual val passwordHashDaoModule = module {
    singleOf(::PrefsPasswordHashDao) bind PasswordHashDao::class
}
