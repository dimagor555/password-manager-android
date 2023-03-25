package ru.dimagor555.password.domain.folder

import kotlinx.serialization.Serializable
import ru.dimagor555.password.domain.password.Password

@Serializable
sealed interface ChildId {

    @Serializable
    data class PasswordId(val id: String) : ChildId

    @Serializable
    data class FolderId(val id: String) : ChildId
}

fun Password.toChildId(): ChildId = ChildId.PasswordId(this.id!!)