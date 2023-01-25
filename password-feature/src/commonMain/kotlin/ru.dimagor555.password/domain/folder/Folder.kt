package ru.dimagor555.password.domain.folder

import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.Parent
import ru.dimagor555.password.domain.metadata.FolderMetadata
import ru.dimagor555.password.domain.password.field.ShortTextField
import ru.dimagor555.password.domain.password.field.TITLE_FIELD_KEY

data class Folder(
    override val id: String? = null,
    val title: ShortTextField = ShortTextField(TITLE_FIELD_KEY),
    val metadata: FolderMetadata = FolderMetadata(),
    override val children: Set<Child> = emptySet(),
) : Parent {

    companion object {
        internal const val ROOT_FOLDER_ID: String = "516fa7b2-ca40-4264-a24e-c1367880a1de"
    }
}
