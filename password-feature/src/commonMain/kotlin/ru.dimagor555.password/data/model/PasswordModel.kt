package ru.dimagor555.password.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import ru.dimagor555.password.data.getUuid
import ru.dimagor555.password.data.model.metadata.PasswordMetadataModel
import ru.dimagor555.password.data.model.metadata.toPasswordMetadata
import ru.dimagor555.password.data.model.metadata.toPasswordMetadataModel
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.PasswordFields

class PasswordModel(
    @PrimaryKey
    var id: RealmUUID = RealmUUID.random(),
    var metadata: PasswordMetadataModel? = PasswordMetadataModel(),
    var fields: Set<FieldModel> = emptySet(),
) : RealmObject {
    constructor() : this(
        id = RealmUUID.random(),
        metadata = PasswordMetadataModel(),
        fields = emptySet(),
    )
}

fun Password.toPasswordModel() = PasswordModel(
    id = getUuid(id),
    metadata = metadata.toPasswordMetadataModel(),
    fields = fields.fields.map { it.toFieldModel() }.toSet()
)

fun PasswordModel.toPassword() = Password(
    id = id.toString(),
    metadata = metadata!!.toPasswordMetadata(),
    fields = PasswordFields(
        fields.mapNotNull { it.toField() }.toSet()
    )
)
