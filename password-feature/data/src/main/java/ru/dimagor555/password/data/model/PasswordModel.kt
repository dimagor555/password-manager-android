package ru.dimagor555.password.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.dimagor555.password.domain.Password

@Entity
internal data class PasswordModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val login: String,
    val encryptedPassword: String,
    val isFavourite: Boolean,
    val creationTimestamp: Long,
    val editingTimestamp: Long,
)

internal fun Password.toPasswordModel() = PasswordModel(
    id = id,
    title = title,
    login = login,
    encryptedPassword = encryptedPassword,
    isFavourite = isFavourite,
    creationTimestamp = creationDateTime.toEpochMilliseconds(),
    editingTimestamp = editingDateTime.toEpochMilliseconds()
)