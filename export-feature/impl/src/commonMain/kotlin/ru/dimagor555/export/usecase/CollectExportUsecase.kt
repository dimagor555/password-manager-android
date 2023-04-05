package ru.dimagor555.export.usecase

import ru.dimagor555.export.domain.Export
import ru.dimagor555.export.usecase.repository.PasswordsAndFolderChildrenRepository

internal class CollectExportUsecase(
    private val passwordsAndFolderChildrenRepository: PasswordsAndFolderChildrenRepository,
) {

    suspend operator fun invoke(): Export? =
        passwordsAndFolderChildrenRepository
            .getOrNull()
            ?.let { Export(passwordsAndFolderChildren = it) }
}