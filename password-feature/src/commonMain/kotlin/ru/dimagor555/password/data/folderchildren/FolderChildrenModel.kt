package ru.dimagor555.password.data.folderchildren

import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import ru.dimagor555.password.data.getUuid
import ru.dimagor555.password.data.model.ChildIdModel
import ru.dimagor555.password.data.model.ChildIdType
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.FolderChildren

class FolderChildrenModel(
    @PrimaryKey
    var parentId: RealmUUID = RealmUUID.random(),
    // TODO rename to childIds, why nullable?
    var childrenIds: RealmSet<ChildIdModel>? = null,
) : RealmObject {
    constructor() : this(
        parentId = RealmUUID.random(),
        childrenIds = null,
    )
}

fun FolderChildrenModel.toFolderChildren() = FolderChildren(
    parentId = parentId.toString(),
    childrenIds = childrenIds!!.map {
        when(it.type) {
            ChildIdType.PASSWORD -> ChildId.PasswordId(it.id.toString())
            ChildIdType.FOLDER -> ChildId.FolderId(it.id.toString())
        }
    }.toSet(),
)

fun FolderChildren.toFolderChildrenModel() = FolderChildrenModel(
    parentId = getUuid(parentId),
    childrenIds = childrenIds.map {
        when(it) {
            is ChildId.FolderId -> ChildIdModel(getUuid(it.id), ChildIdType.FOLDER)
            is ChildId.PasswordId -> ChildIdModel(getUuid(it.id), ChildIdType.PASSWORD)
        }
    }.toRealmSet(),
)
