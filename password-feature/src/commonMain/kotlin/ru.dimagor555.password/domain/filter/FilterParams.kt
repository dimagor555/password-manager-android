package ru.dimagor555.password.domain.filter

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.metadata.UsageHistory
import ru.dimagor555.password.domain.password.field.LongTextField
import ru.dimagor555.password.domain.password.field.ShortTextField
import ru.dimagor555.password.domain.password.*

data class FilterParams(
    val id: String?,
    val titleSortValue: String,
    val searchValues: Set<String>,
    val isFavourite: Boolean = false,
    val creationDateTime: Instant = Clock.System.now(),
    val editingDateTime: Instant = Clock.System.now(),
    val usageHistory: UsageHistory = UsageHistory()
) {
    init {
        require(this.searchValues.isNotEmpty()) {
            "Must not be empty"
        }
    }
}

fun Password.toFilterParams(): FilterParams = FilterParams(
    id = id,
    titleSortValue = fields.title.text,
    searchValues = fields.sortSearchValues(),
    isFavourite = metadata.isFavourite,
    creationDateTime = metadata.creationDateTime,
    editingDateTime = metadata.editingDateTime,
    usageHistory = metadata.usageHistory,
)

fun PasswordFields.sortSearchValues(): Set<String> {
    val shortTextFields = fields.filterIsInstance<ShortTextField>().map { it.text }
    val longTextFields = fields.filterIsInstance<LongTextField>().map { it.text }
    return setOfNotNull(title.text, site?.text, login?.text, phone?.text) + shortTextFields + longTextFields
}

fun Folder.toFilterParams(): FilterParams = FilterParams(
    id = id,
    titleSortValue = title.text,
    searchValues = setOf(title.text),
    creationDateTime = metadata.creationDateTime,
    editingDateTime = metadata.editingDateTime,
    usageHistory = this.getChildrenUsageHistory(),
)

fun Folder.getChildrenUsageHistory(): UsageHistory {
    val childrenUsageHistory = children
        .filterIsInstance<Password>()
        .map { it.metadata.usageHistory }
    val usagesCount = childrenUsageHistory.sumOf { it.usagesCount }
    val lastUpdate = childrenUsageHistory.maxOf { it.lastUsageDateTime }
    return UsageHistory(
        usagesCount = usagesCount,
        lastUsageDateTime = lastUpdate,
    )
}
