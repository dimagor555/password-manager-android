package ru.dimagor555.password.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.password.data.db.PasswordDatabase
import ru.dimagor555.password.data.repository.InMemoryPasswordFilterRepository
import ru.dimagor555.password.data.repository.RealmFolderChildrenRepository
import ru.dimagor555.password.data.repository.RealmFolderRepository
import ru.dimagor555.password.data.repository.RealmPasswordRepository
import ru.dimagor555.password.repository.FilterRepository
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildrenRepository
import ru.dimagor555.password.repository.FolderRepository
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordUseCases
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordUseCases
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsUseCases
import ru.dimagor555.password.ui.editscreen.model.EditPasswordUseCases
import ru.dimagor555.password.ui.listscreen.model.PasswordListUseCases
import ru.dimagor555.password.usecase.field.CopyPasswordUseCase
import ru.dimagor555.password.usecase.field.DecryptPasswordUseCase
import ru.dimagor555.password.usecase.password.single.CreatePasswordUseCase
import ru.dimagor555.password.usecase.password.single.UpdatePasswordUseCase

val passwordModule = module {
    includes(passwordPlatformModule)

    single { PasswordDatabase().realm }
    factoryOf(::RealmPasswordRepository) bind PasswordRepository::class
    factoryOf(::RealmFolderChildrenRepository) bind FolderChildrenRepository::class
    factoryOf(::RealmFolderRepository) bind FolderRepository::class
    singleOf(::InMemoryPasswordFilterRepository) bind FilterRepository::class

    factoryOf(::UpdatePasswordUseCase)
    factoryOf(::CreatePasswordUseCase)
    factoryOf(::CopyPasswordUseCase)
    factoryOf(::DecryptPasswordUseCase)

    factoryOf(::CreatePasswordUseCases)
    factoryOf(::CommonEditPasswordUseCases)
    factoryOf(::PasswordDetailsUseCases)
    factoryOf(::EditPasswordUseCases)
    factoryOf(::PasswordListUseCases)
}
