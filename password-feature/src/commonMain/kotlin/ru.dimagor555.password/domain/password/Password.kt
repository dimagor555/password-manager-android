package ru.dimagor555.password.domain.password

import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.metadata.PasswordMetadata

data class Password(
    val id: String? = null,
    val parentId: String? = null,
    val fields: PasswordFields,
    val metadata: PasswordMetadata = PasswordMetadata(),
) : Child

fun Password.getUniqueIdentifier(): String =
    if (fields.login != null) {
        fields.login!!.text
    } else {
        fields.phone!!.text
    }
