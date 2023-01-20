package ru.dimagor555.password.data.model.metadata

import io.realm.kotlin.types.RealmObject
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.dimagor555.password.domain.metadata.PasswordMetadata

class PasswordMetadataModel(
    var isFavourite: Boolean = false,
    var creationDateTime: Instant = Clock.System.now(),
    var editingDateTime: Instant = Clock.System.now(),
    var usageHistory: UsageHistoryModel? = UsageHistoryModel(),
) : RealmObject {
    constructor() : this(
        isFavourite = false,
        creationDateTime = Clock.System.now(),
        editingDateTime = Clock.System.now(),
        usageHistory = UsageHistoryModel(),
    )
}

fun PasswordMetadata.toPasswordMetadataModel() = PasswordMetadataModel(
    isFavourite = isFavourite,
    creationDateTime = creationDateTime,
    editingDateTime = editingDateTime,
    usageHistory = usageHistory.toUsageHistoryModel(),
)

fun PasswordMetadataModel.toPasswordMetadata() = PasswordMetadata(
    isFavourite = isFavourite,
    creationDateTime = creationDateTime,
    editingDateTime = editingDateTime,
    usageHistory = usageHistory!!.toUsageHistory(),
)
