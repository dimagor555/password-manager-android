package ru.dimagor555.password.data.model

import io.realm.kotlin.types.RealmObject
import ru.dimagor555.password.domain.password.field.Field
import ru.dimagor555.password.domain.password.field.copy

class FieldModel(
    var key: String = "",
    var text: String = "",
) : RealmObject {
    constructor() : this(key = "", text = "")
}

fun Field.toFieldModel() = FieldModel(
    key = key,
    text = text,
)

fun FieldModel.toField() = Field.createFieldByKey(
    key = key
)?.copy(text = text)
