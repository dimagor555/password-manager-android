package ru.dimagor555.password.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.Usage

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PasswordModel::class,
            parentColumns = ["id"],
            childColumns = ["passwordId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("passwordId")
    ]
)
/*
    TODO: in order to avoid problems with access to the database when obfuscation will be enabled,
          it's mandatory to use @ColumnInfo annotation. As you already know, only strings
          are not being obfuscated
 */
internal data class UsageModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val timestamp: Long,
    val passwordId: Int
)

internal fun UsageModel.toUsage() = Usage(
    id = id,
    datetime = Instant.fromEpochMilliseconds(timestamp)
)

internal fun Password.toUsageModels() = usages.map { usage ->
    UsageModel(
        id = usage.id,
        timestamp = usage.datetime.toEpochMilliseconds(),
        passwordId = this.id!!
    )
}