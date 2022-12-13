package ru.dimagor555.password.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import ru.dimagor555.password.data.getUuid
import ru.dimagor555.password.domain.folder.ChildId

sealed interface ChildIdModel {
    val id: RealmUUID

    @JvmInline
    value class PasswordIdModel(override val id: RealmUUID): ChildIdModel, RealmObject

    @JvmInline
    value class FolderIdModel(override val id: RealmUUID): ChildIdModel, RealmObject
}

fun ChildId.toChildIdModel(): ChildIdModel = when(this) {
    is ChildId.PasswordId -> ChildIdModel.PasswordIdModel(getUuid(this.id))
    is ChildId.FolderId -> ChildIdModel.FolderIdModel(getUuid(this.id))
}
