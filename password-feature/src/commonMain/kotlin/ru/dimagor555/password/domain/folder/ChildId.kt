package ru.dimagor555.password.domain.folder

import kotlinx.serialization.Serializable
import ru.dimagor555.password.domain.password.Password

@Serializable
sealed interface ChildId {

    @JvmInline
    @Serializable
    value class PasswordId(val id: String) : ChildId

    @JvmInline
    @Serializable
    value class FolderId(val id: String) : ChildId
}

fun Password.toChildId(): ChildId = ChildId.PasswordId(this.id!!)