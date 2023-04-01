package ru.dimagor555.password.usecase.folderchildren.repository

import ru.dimagor555.password.domain.folder.ChildId

sealed interface ChangeFolderParam {

    val childId: ChildId
    val fromParentId: String?
    val toParentId: String?

    data class Add(
        override val childId: ChildId,
        override val toParentId: String,
    ) : ChangeFolderParam {

        override val fromParentId: String?
            get() = null
    }

    data class Remove(
        override val childId: ChildId,
        override val fromParentId: String,
    ) : ChangeFolderParam {

        override val toParentId: String?
            get() = null
    }

    data class Move(
        override val childId: ChildId,
        override val fromParentId: String,
        override val toParentId: String,
    ) : ChangeFolderParam
}