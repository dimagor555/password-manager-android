package ru.dimagor555.password.data.folder

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import ru.dimagor555.password.data.toInstant
import ru.dimagor555.password.data.toRealmInstant
import ru.dimagor555.password.domain.metadata.FolderMetadata

class FolderMetadataModel(
    var creationDateTime: RealmInstant = RealmInstant.now(),
    var editingDateTime: RealmInstant = RealmInstant.now(),
) : RealmObject {
    constructor() : this(
        creationDateTime = RealmInstant.now(),
        editingDateTime = RealmInstant.now(),
    )
}

fun FolderMetadata.toFolderMetadataModel() = FolderMetadataModel(
    creationDateTime = creationDateTime.toRealmInstant(),
    editingDateTime = editingDateTime.toRealmInstant(),
)

fun FolderMetadataModel.toFolderMetadata() = FolderMetadata(
    creationDateTime = creationDateTime.toInstant(),
    editingDateTime = editingDateTime.toInstant(),
)
