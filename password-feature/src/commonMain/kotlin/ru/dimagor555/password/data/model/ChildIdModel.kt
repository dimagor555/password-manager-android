package ru.dimagor555.password.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import ru.dimagor555.password.data.getUuid
import ru.dimagor555.password.domain.folder.ChildId

enum class ChildIdType {
    PASSWORD, FOLDER
}

class ChildIdModel(
    var id: RealmUUID = RealmUUID.random(),
    var type: ChildIdType = ChildIdType.PASSWORD,
) : RealmObject {
    constructor() : this(
        id = RealmUUID.random(),
        type = ChildIdType.PASSWORD,
    )
}

fun ChildId.toChildIdModel(): ChildIdModel = when (this) {
    is ChildId.PasswordId -> ChildIdModel(getUuid(this.id), ChildIdType.PASSWORD)
    is ChildId.FolderId -> ChildIdModel(getUuid(this.id), ChildIdType.FOLDER)
}

fun ChildIdModel.toChildId(): ChildId {
    val id = this.id.toString()
    return when (this.type) {
        ChildIdType.PASSWORD -> ChildId.PasswordId(id)
        ChildIdType.FOLDER -> ChildId.FolderId(id)
    }
}