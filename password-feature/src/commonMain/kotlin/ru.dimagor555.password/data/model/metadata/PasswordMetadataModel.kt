package ru.dimagor555.password.data.model.metadata

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import ru.dimagor555.password.data.toInstant
import ru.dimagor555.password.data.toRealmInstant
import ru.dimagor555.password.domain.metadata.PasswordMetadata

class PasswordMetadataModel(
    var isFavourite: Boolean = false,
    var creationDateTime: RealmInstant = RealmInstant.now(),
    var editingDateTime: RealmInstant = RealmInstant.now(),
    var usageHistory: UsageHistoryModel? = null,
) : RealmObject {
    constructor() : this(
        isFavourite = false,
        creationDateTime = RealmInstant.now(),
        editingDateTime = RealmInstant.now(),
        usageHistory = null,
    )
}

fun PasswordMetadata.toPasswordMetadataModel() = PasswordMetadataModel(
    isFavourite = isFavourite,
    creationDateTime = creationDateTime.toRealmInstant(),
    editingDateTime = editingDateTime.toRealmInstant(),
    usageHistory = usageHistory.toUsageHistoryModel(),
)

fun PasswordMetadataModel.toPasswordMetadata() = PasswordMetadata(
    isFavourite = isFavourite,
    creationDateTime = creationDateTime.toInstant(),
    editingDateTime = editingDateTime.toInstant(),
    usageHistory = usageHistory!!.toUsageHistory(),
)
