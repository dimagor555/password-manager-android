package ru.dimagor555.synchronization.domain.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
sealed interface SyncResponse {

    @Serializable
    data class CommonSyncResponse(
        val passwordsAndFolderChildren: JsonObject,
        val requestingPasswordRecordIds: List<String>,
    ) : SyncResponse

    @Serializable
    data class SimpleRespondSyncResponse(
        val passwordsAndFolderChildren: JsonObject,
    ) : SyncResponse

    @Serializable
    data class SimpleRequestingSyncResponse(
        val requestingPasswordRecordIds: List<String>,
    ) : SyncResponse

    @Serializable
    object SuccessResponse : SyncResponse
}