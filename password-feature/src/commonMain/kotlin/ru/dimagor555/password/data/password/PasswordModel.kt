package ru.dimagor555.password.data.password

import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import ru.dimagor555.password.data.getUuid
import ru.dimagor555.password.data.model.FieldModel
import ru.dimagor555.password.data.model.toField
import ru.dimagor555.password.data.model.toFieldModel
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.PasswordFields

class PasswordModel(
    @PrimaryKey
    var id: RealmUUID = RealmUUID.random(),
    var metadata: PasswordMetadataModel? = null,
    var fields: RealmSet<FieldModel>? = null,
) : RealmObject {
    constructor() : this(
        id = RealmUUID.random(),
        metadata = null,
        fields = null,
    )
}

fun Password.toPasswordModel() = PasswordModel(
    id = getUuid(id),
    metadata = metadata.toPasswordMetadataModel(),
    fields = fields.fields.map { it.toFieldModel() }.toRealmSet()
)

fun PasswordModel.toPassword() = Password(
    id = id.toString(),
    metadata = metadata!!.toPasswordMetadata(),
    fields = PasswordFields(
        fields!!.mapNotNull { it.toField() }.toSet()
    )
)
