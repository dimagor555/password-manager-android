package ru.dimagor555.password.data.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.password.data.db.PasswordDatabase
import ru.dimagor555.password.data.repository.InMemoryPasswordFilterRepository
import ru.dimagor555.password.data.repository.RealmFolderChildrenRepository
import ru.dimagor555.password.data.repository.RealmFolderRepository
import ru.dimagor555.password.data.repository.RealmPasswordRepository
import ru.dimagor555.password.repository.*

val passwordDataModule = module {
    single { PasswordDatabase().realm }
    includes(clipboardModule)
    factoryOf(::RealmPasswordRepository) bind PasswordRepository::class
    factoryOf(::RealmFolderChildrenRepository) bind FolderChildrenRepository::class
    factoryOf(::RealmFolderRepository) bind FolderRepository::class
    singleOf(::InMemoryPasswordFilterRepository) bind FilterRepository::class
}
