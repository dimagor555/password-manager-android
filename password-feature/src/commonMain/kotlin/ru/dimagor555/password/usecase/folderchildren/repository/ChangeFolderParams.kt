package ru.dimagor555.password.usecase.folderchildren.repository

import ru.dimagor555.password.domain.folder.ChildId

data class ChangeFolderParams(
    val childId: ChildId,
    val fromParentId: String,
    val toParentId: String,
)