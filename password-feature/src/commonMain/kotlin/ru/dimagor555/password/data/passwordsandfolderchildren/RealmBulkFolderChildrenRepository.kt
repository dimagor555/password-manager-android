package ru.dimagor555.password.data.passwordsandfolderchildren

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.first
import ru.dimagor555.password.data.folderchildren.FolderChildrenModel
import ru.dimagor555.password.data.folderchildren.toFolderChildren
import ru.dimagor555.password.data.model.ChildIdModel
import ru.dimagor555.password.data.model.toChildId
import ru.dimagor555.password.data.model.toChildIdModel
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParam
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository

private typealias ChildIdsByParentIds = Map<String, MutableSet<ChildId>>

class RealmBulkFolderChildrenRepository(
    private val realm: Realm,
) : BulkFolderChildrenRepository {

    override suspend fun getAll(): List<FolderChildren> = realm
        .query<FolderChildrenModel>()
        .asFlow()
        .first()
        .list
        .map { it.toFolderChildren() }

    override suspend fun changeAllChildrenFolders(params: List<ChangeFolderParam>) =
        updateAllFolderChildren { childIdsByParentIds ->
            params.forEach { childIdsByParentIds.changeChildFolder(it) }
        }

    private suspend fun updateAllFolderChildren(update: (ChildIdsByParentIds) -> Unit) =
        realm.write {
            val allFolderChildren = this.query<FolderChildrenModel>().find()
            val childIdsByParentIds = allFolderChildren
                .associateBy { it.parentId.toString() }
                .mapValues {
                    it
                        .value
                        .childrenIds
                        .orEmpty() // TODO why childrenIds nullable?
                        .map(ChildIdModel::toChildId)
                        .toMutableSet()
                }
            update(childIdsByParentIds)
            childIdsByParentIds.writeUpdateInto(allFolderChildren)
        }

    private fun ChildIdsByParentIds.writeUpdateInto(
        folderChildren: RealmResults<FolderChildrenModel>,
    ) = this.forEach { (parentId, childIds) ->
        folderChildren
            .find { it.parentId.toString() == parentId }
            ?.childrenIds = childIds.map(ChildId::toChildIdModel).toRealmSet()
    }

    private fun ChildIdsByParentIds.changeChildFolder(
        params: ChangeFolderParam,
    ) {
        val fromChildIds = this[params.fromParentId]
        val toChildIds = this[params.toParentId]
        fromChildIds?.remove(params.childId)
        toChildIds?.add(params.childId)
    }

    override suspend fun removeAllChildrenFromAllFolders() = realm.write {
        this
            .query<FolderChildrenModel>()
            .find()
            .forEach { it.childrenIds = realmSetOf() }
    }
}