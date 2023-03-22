package ru.dimagor555.password.domain.folder

import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.metadata.FolderMetadata
import ru.dimagor555.password.domain.password.field.ShortTextField
import ru.dimagor555.password.domain.password.field.TITLE_FIELD_KEY

class FolderDescriptor(
    override var id: String = "",
    var title: String = "",
    var metadata: FolderMetadata = FolderMetadata(),
): Child

fun FolderDescriptor.toFolder(children: Set<Child>) = Folder(
    id = id,
    title = ShortTextField(TITLE_FIELD_KEY, title),
    metadata = metadata,
    children = children,
)
