package ru.dimagor555.password.domain.password

import kotlinx.serialization.Serializable
import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.metadata.PasswordMetadata

@Serializable
data class Password(
    override val id: String? = null,
    val fields: PasswordFields,
    val metadata: PasswordMetadata = PasswordMetadata(),
) : Child

// TODO bad name
fun Password.getUniqueIdentifier(): String =
    if (fields.login != null) {
        fields.login!!.text
    } else {
        fields.phone!!.text
    }
