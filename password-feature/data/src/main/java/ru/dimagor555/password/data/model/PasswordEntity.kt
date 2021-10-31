package ru.dimagor555.password.data.model

import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.datetime.Instant
import ru.dimagor555.password.domain.Password

internal data class PasswordEntity(
    @Embedded val passwordModel: PasswordModel,
    @Relation(parentColumn = "id", entityColumn = "passwordId")
    val usageModels: List<UsageModel>
)

internal fun PasswordEntity.toPassword() = Password(
    id = passwordModel.id,
    title = passwordModel.title,
    login = passwordModel.login,
    encryptedPassword = passwordModel.encryptedPassword,
    isFavourite = passwordModel.isFavourite,
    creationDateTime = Instant.fromEpochMilliseconds(passwordModel.creationTimestamp),
    editingDateTime = Instant.fromEpochMilliseconds(passwordModel.editingTimestamp),
    usages = usageModels.map(UsageModel::toUsage)
)

internal fun Password.toPasswordEntity() = PasswordEntity(
    passwordModel = this.toPasswordModel(),
    usageModels = this.toUsageModels()
)