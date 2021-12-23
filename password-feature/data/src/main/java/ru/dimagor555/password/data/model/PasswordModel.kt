package ru.dimagor555.password.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.PasswordMetadata
import ru.dimagor555.password.domain.UsageHistory

@Entity(tableName = "password_model")
internal data class PasswordModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val login: String,
    val encryptedPassword: String,
    val isFavourite: Boolean,
    val creationTimestamp: Long,
    val editingTimestamp: Long,
    val usagesCount: Int,
    val lastUsageTimestamp: Long
)

internal fun Password.toPasswordModel() = PasswordModel(
    id = id,
    title = title,
    login = login,
    encryptedPassword = encryptedPassword,
    isFavourite = metadata.isFavourite,
    creationTimestamp = metadata.creationDateTime.toEpochMilliseconds(),
    editingTimestamp = metadata.editingDateTime.toEpochMilliseconds(),
    usagesCount = metadata.usageHistory.usagesCount,
    lastUsageTimestamp = metadata.usageHistory.lastUsageDateTime.toEpochMilliseconds()
)

internal fun PasswordModel.toPassword() = Password(
    id = id,
    title = title,
    login = login,
    encryptedPassword = encryptedPassword,
    metadata = this.mapToMetadata()
)

private fun PasswordModel.mapToMetadata() = PasswordMetadata(
    isFavourite = isFavourite,
    creationDateTime = creationTimestamp.toInstant(),
    editingDateTime = editingTimestamp.toInstant(),
    usageHistory = UsageHistory(
        usagesCount = usagesCount,
        lastUsageDateTime = lastUsageTimestamp.toInstant()
    )
)

private fun Long.toInstant() = Instant.fromEpochMilliseconds(this)
