package ru.dimagor555.password.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import ru.dimagor555.password.data.getUuid
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.FolderChildren

class FolderChildrenModel(
    @PrimaryKey
    var parentId: RealmUUID = RealmUUID.random(),
    var childrenIds: Set<ChildIdModel> = setOf(),
) : RealmObject {
    constructor() : this(
        parentId = RealmUUID.random(),
        childrenIds = setOf(),
    )
}

fun FolderChildrenModel.toFolderChildren() = FolderChildren(
    parentId = parentId.toString(),
    childrenIds = childrenIds.map {
        when(it) {
            is ChildIdModel.FolderIdModel -> ChildId.FolderId(it.id.toString())
            is ChildIdModel.PasswordIdModel -> ChildId.PasswordId(it.id.toString())
        }
    }.toSet(),
)

fun FolderChildren.toFolderChildrenModel() = FolderChildrenModel(
    parentId = getUuid(parentId),
    childrenIds = childrenIds.map {
        when(it) {
            is ChildId.FolderId -> ChildIdModel.FolderIdModel(getUuid(it.id))
            is ChildId.PasswordId -> ChildIdModel.PasswordIdModel(getUuid(it.id))
        }
    }.toSet(),
)
