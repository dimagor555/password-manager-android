package ru.dimagor555.password.ui.listscreen.model

import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.password.usecase.field.repository.ClipboardRepository
import ru.dimagor555.password.usecase.folder.repository.FolderRepository
import ru.dimagor555.password.usecase.field.CopyPasswordUseCase
import ru.dimagor555.password.usecase.filter.ObserveFilterStateUseCase
import ru.dimagor555.password.usecase.filter.UpdateFilterStateUseCase
import ru.dimagor555.password.usecase.filter.repository.FilterRepository
import ru.dimagor555.password.usecase.folder.CreateFolderUseCase
import ru.dimagor555.password.usecase.folder.GetFolderUseCase
import ru.dimagor555.password.usecase.folderchildren.ObserveFolderChildrenUseCase
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildrenRepository
import ru.dimagor555.password.usecase.password.many.GetPasswordsByIdsUseCase
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.password.single.ToggleFavouriteUseCase

internal class PasswordListUseCases(
    passwordRepository: PasswordRepository,
    filterRepository: FilterRepository,
    clipboardRepository: ClipboardRepository,
    decryptor: SymmetricDecryptor,
    folderRepository: FolderRepository,
    folderChildrenRepository: FolderChildrenRepository,
) {
    val getPasswordsByIdsUseCase = GetPasswordsByIdsUseCase(passwordRepository)

    val observePasswordFilterState = ObserveFilterStateUseCase(filterRepository)
    val updatePasswordFilterState = UpdateFilterStateUseCase(filterRepository)

    val toggleFavourite = ToggleFavouriteUseCase(passwordRepository)
    val copyPassword = CopyPasswordUseCase(passwordRepository, clipboardRepository, decryptor)

    val getFolderUseCase = GetFolderUseCase(folderRepository)
    val observeFolderChildrenUseCase = ObserveFolderChildrenUseCase(folderChildrenRepository)
    val createFolderUseCase = CreateFolderUseCase(folderRepository, folderChildrenRepository)
}