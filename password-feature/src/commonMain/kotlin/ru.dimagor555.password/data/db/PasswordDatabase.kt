package ru.dimagor555.password.data.db

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import ru.dimagor555.password.data.model.*
import ru.dimagor555.password.data.model.metadata.FolderMetadataModel
import ru.dimagor555.password.data.model.metadata.PasswordMetadataModel
import ru.dimagor555.password.data.model.metadata.UsageHistoryModel

class PasswordDatabase {

    private val schema = setOf(
        PasswordModel::class,
        FolderModel::class,
        FolderChildrenModel::class,
        ChildIdModel.PasswordIdModel::class,
        ChildIdModel.FolderIdModel::class,
        FieldModel::class,
        FolderMetadataModel::class,
        PasswordMetadataModel::class,
        UsageHistoryModel::class,
    )

    private val configuration = RealmConfiguration.Builder(schema)
        .schemaVersion(schemaVersion)
        .build()

    val realm = Realm.open(configuration)

    companion object {
        const val schemaVersion = 1L
    }
}
