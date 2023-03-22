package ru.dimagor555.password.usecase.folderchildren.repository

import ru.dimagor555.password.domain.folder.ChildId

data class FolderChildParams(
    val parentId: String,
    val childId: ChildId,
)