package ru.dimagor555.password.data.folder

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import ru.dimagor555.password.data.getUuid
import ru.dimagor555.password.data.model.FieldModel
import ru.dimagor555.password.data.model.toFieldModel
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.folder.FolderDescriptor

class FolderModel(
    @PrimaryKey
    var id: RealmUUID = RealmUUID.random(),
    var title: FieldModel? = null,
    var metadata: FolderMetadataModel? = null,
) : RealmObject {
    constructor() : this(
        id = RealmUUID.random(),
        title = null,
        metadata = null,
    )
}

fun Folder.toFolderModel() = FolderModel(
    id = getUuid(id),
    title = title.toFieldModel(),
    metadata = metadata.toFolderMetadataModel(),
)

fun FolderModel.toFolderDescriptor() = FolderDescriptor(
    id = id.toString(),
    title = title!!.text,
    metadata = metadata!!.toFolderMetadata(),
)
