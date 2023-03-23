package ru.dimagor555.password.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.password.data.db.PasswordDatabase
import ru.dimagor555.password.data.filter.InMemoryPasswordFilterRepository
import ru.dimagor555.password.data.folder.RealmFolderRepository
import ru.dimagor555.password.data.folderchildren.RealmFolderChildrenRepository
import ru.dimagor555.password.data.password.RealmPasswordRepository
import ru.dimagor555.password.data.passwordsandfolderchildren.RealmBulkFolderChildrenRepository
import ru.dimagor555.password.data.passwordsandfolderchildren.RealmBulkPasswordRepository
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordUseCases
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordUseCases
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsUseCases
import ru.dimagor555.password.ui.editscreen.model.EditPasswordUseCases
import ru.dimagor555.password.ui.listscreen.model.PasswordListUseCases
import ru.dimagor555.password.usecase.field.CopyPasswordUseCase
import ru.dimagor555.password.usecase.field.DecryptPasswordUseCase
import ru.dimagor555.password.usecase.filter.repository.FilterRepository
import ru.dimagor555.password.usecase.folder.repository.FolderRepository
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildrenRepository
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.password.single.CreatePasswordUseCase
import ru.dimagor555.password.usecase.password.single.UpdatePasswordUseCase
import ru.dimagor555.password.usecase.passwordsandfolderchildren.*
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

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

    factoryOf(::RealmBulkPasswordRepository) bind BulkPasswordRepository::class
    factoryOf(::RealmBulkFolderChildrenRepository) bind BulkFolderChildrenRepository::class
    factoryOf(::GetPasswordsAndFolderChildrenUsecase)
    factoryOf(::AddPasswordsAndFolderChildrenUsecase)
    factoryOf(::UpdatePasswordsAndFolderChildrenUsecase)
    factoryOf(::AddOrUpdatePasswordsAndFolderChildrenUsecase)
    factoryOf(::RemovePasswordsAndFolderChildrenUsecase)
}
