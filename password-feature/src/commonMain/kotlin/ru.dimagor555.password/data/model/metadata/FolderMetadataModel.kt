package ru.dimagor555.password.data.model.metadata

import io.realm.kotlin.types.RealmObject
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.dimagor555.password.domain.metadata.FolderMetadata

class FolderMetadataModel(
    var creationDateTime: Instant = Clock.System.now(),
    var editingDateTime: Instant = Clock.System.now(),
) : RealmObject

fun FolderMetadata.toFolderMetadataModel() = FolderMetadataModel(
    creationDateTime = creationDateTime,
    editingDateTime = editingDateTime,
)

fun FolderMetadataModel.toFolderMetadata() = FolderMetadata(
    creationDateTime = creationDateTime,
    editingDateTime = editingDateTime,
)
