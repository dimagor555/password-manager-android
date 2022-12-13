package ru.dimagor555.password.data.model.metadata

import io.realm.kotlin.types.RealmObject
import kotlinx.datetime.Instant
import ru.dimagor555.password.domain.metadata.UsageHistory

class UsageHistoryModel(
    var usagesCount: Int = 0,
    var lastUsageDateTime: Instant = Instant.DISTANT_PAST,
) : RealmObject

fun UsageHistory.toUsageHistoryModel() = UsageHistoryModel(
    usagesCount = usagesCount,
    lastUsageDateTime = lastUsageDateTime,
)

fun UsageHistoryModel.toUsageHistory() = UsageHistory(
    usagesCount = usagesCount,
    lastUsageDateTime = lastUsageDateTime,
)
