package ru.dimagor555.password.data.db

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import ru.dimagor555.password.data.folder.FolderMetadataModel
import ru.dimagor555.password.data.folder.FolderModel
import ru.dimagor555.password.data.folderchildren.FolderChildrenModel
import ru.dimagor555.password.data.model.ChildIdModel
import ru.dimagor555.password.data.model.FieldModel
import ru.dimagor555.password.data.password.PasswordMetadataModel
import ru.dimagor555.password.data.password.PasswordModel
import ru.dimagor555.password.data.password.UsageHistoryModel

// TODO extract DB to separate module
class PasswordDatabase {

    private val schema = setOf(
        PasswordModel::class,
        FolderModel::class,
        FolderChildrenModel::class,
        ChildIdModel::class,
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
