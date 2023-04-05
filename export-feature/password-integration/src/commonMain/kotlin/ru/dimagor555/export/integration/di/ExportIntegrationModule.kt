package ru.dimagor555.export.integration.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.export.integration.data.PasswordsAndFolderChildrenRepositoryImpl
import ru.dimagor555.export.usecase.repository.PasswordsAndFolderChildrenRepository

val exportIntegrationModule = module {
    factoryOf(::PasswordsAndFolderChildrenRepositoryImpl) bind
            PasswordsAndFolderChildrenRepository::class
}