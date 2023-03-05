package ru.dimagor555.password.data.model.metadata

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import ru.dimagor555.password.data.toInstant
import ru.dimagor555.password.data.toRealmInstant
import ru.dimagor555.password.domain.metadata.UsageHistory

class UsageHistoryModel(
    var usagesCount: Int = 0,
    var lastUsageDateTime: RealmInstant = RealmInstant.now(),
) : RealmObject {
    constructor() : this(usagesCount = 0, lastUsageDateTime = RealmInstant.now())
}

fun UsageHistory.toUsageHistoryModel() = UsageHistoryModel(
    usagesCount = usagesCount,
    lastUsageDateTime = lastUsageDateTime.toRealmInstant(),
)

fun UsageHistoryModel.toUsageHistory() = UsageHistory(
    usagesCount = usagesCount,
    lastUsageDateTime = lastUsageDateTime.toInstant(),
)
