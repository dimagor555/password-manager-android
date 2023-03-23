package ru.dimagor555.password.domain.folder

import ru.dimagor555.password.domain.password.Password

sealed interface ChildId {

    @JvmInline
    value class PasswordId(val id: String) : ChildId

    @JvmInline
    value class FolderId(val id: String) : ChildId
}

fun Password.toChildId(): ChildId = ChildId.PasswordId(this.id!!)