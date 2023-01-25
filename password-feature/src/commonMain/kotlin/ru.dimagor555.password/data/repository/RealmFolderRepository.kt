package ru.dimagor555.password.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.dimagor555.password.data.*
import ru.dimagor555.password.data.model.*
import ru.dimagor555.password.data.model.metadata.toFolderMetadataModel
import ru.dimagor555.password.data.model.metadata.toPasswordMetadataModel
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.folder.toFolder
import ru.dimagor555.password.repository.FolderRepository

class RealmFolderRepository(
    private val realm: Realm,
    private val folderChildrenRepository: RealmFolderChildrenRepository,
) : FolderRepository {

    override fun observeAll(): Flow<List<Folder>> =
        realm
            .query<FolderModel>()
            .asFlow()
            .map {
                it.list.toList().map { folderModel ->
                    folderModel
                        .toFolderDescriptor()
                        .toFolder(
                            folderChildrenRepository.getChildObjects(folderModel.id.toString())
                        )
                }
            }
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .conflate()

    override suspend fun observeById(id: String): Flow<Folder?> = flowOf(
        realm
            .query<FolderModel>(FolderModel::id eqId id)
            .find()
            .first()
            .toFolderDescriptor()
            .toFolder(folderChildrenRepository.getChildObjects(id))
    )
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .conflate()

    override suspend fun getById(id: String): Folder =
        realm
            .getById<FolderModel>(id)
            .toFolderDescriptor()
            .toFolder(children = folderChildrenRepository.getChildObjects(id))

    override suspend fun add(folder: Folder): String =
        realm.addOrUpdate(folder.toFolderModel()).id.toString()

    override suspend fun update(folder: Folder) {
        realm.write {
            val oldFolder = this.query<FolderModel>("id == uuid(${folder.id})").first().find()
            if (oldFolder != null) {
                oldFolder.metadata = folder.metadata.toFolderMetadataModel()
                oldFolder.title = folder.title.toFieldModel()
            }
        }
    }

    override suspend fun remove(id: String) {
        realm.removeById<FolderModel>(id)
        removeFolderFolders(folderChildrenRepository.getFolderFolders(id))
    }

    override suspend fun removeFolderFolders(folderIds: List<String>) {
        folderIds.forEach {
            realm.removeById<FolderModel>(it)
        }
    }
}
