package ru.dimagor555.password.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import ru.dimagor555.password.data.getUuid
import ru.dimagor555.password.data.model.metadata.FolderMetadataModel
import ru.dimagor555.password.data.model.metadata.toFolderMetadata
import ru.dimagor555.password.data.model.metadata.toFolderMetadataModel
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.folder.FolderDescriptor

class FolderModel(
    @PrimaryKey
    var id: RealmUUID = RealmUUID.random(),
    var title: FieldModel? = FieldModel(),
    var metadata: FolderMetadataModel? = FolderMetadataModel(),
) : RealmObject {
    constructor() : this(
        id = RealmUUID.random(),
        title = FieldModel(),
        metadata = FolderMetadataModel(),
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
