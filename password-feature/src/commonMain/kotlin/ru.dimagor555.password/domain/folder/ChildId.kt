package ru.dimagor555.password.domain.folder

sealed interface ChildId {

    @JvmInline
    value class PasswordId(val id: String): ChildId

    @JvmInline
    value class FolderId(val id: String): ChildId
}
