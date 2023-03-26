package ru.dimagor555.password.data.passwordsandfolderchildren

import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.first
import ru.dimagor555.password.data.folderchildren.FolderChildrenModel
import ru.dimagor555.password.data.folderchildren.toFolderChildren
import ru.dimagor555.password.data.model.ChildIdModel
import ru.dimagor555.password.data.model.toChildIdModel
import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParams
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildParams
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository

private typealias ChildIdsByParentIds = Map<String, MutableSet<ChildIdModel>>

class RealmBulkFolderChildrenRepository(
    private val realm: Realm,
) : BulkFolderChildrenRepository {

    override suspend fun getAll(): List<FolderChildren> = realm
        .query<FolderChildrenModel>()
        .asFlow()
        .first()
        .list
        .map { it.toFolderChildren() }

    override suspend fun addAllChildrenToFolders(params: List<FolderChildParams>) =
        updateAllFolderChildren { childIdsByParentIds ->
            params
                .groupBy { it.parentId }
                .mapValues { entry ->
                    entry
                        .value
                        .map { it.childId.toChildIdModel() }
                }
                .forEach { (parentId, newChildIds) ->
                    val childIds = childIdsByParentIds[parentId] ?: return@forEach
                    childIds += newChildIds
                }
        }

    private suspend fun updateAllFolderChildren(update: (ChildIdsByParentIds) -> Unit) =
        realm.write {
            val allFolderChildren = this.query<FolderChildrenModel>().find()
            val childIdsByParentIds = allFolderChildren
                .associateBy { it.parentId.toString() }
                .mapValues { it.value.childrenIds.orEmpty().toMutableSet() } // TODO why childrenIds nullable?
            update(childIdsByParentIds)
            childIdsByParentIds.writeUpdateInto(allFolderChildren)
        }

    private fun ChildIdsByParentIds.writeUpdateInto(
        folderChildren: RealmResults<FolderChildrenModel>,
    ) = this.forEach { (parentId, childIds) ->
        folderChildren
            .find { it.parentId.toString() == parentId }
            ?.childrenIds = childIds.toRealmSet()
    }

    override suspend fun changeAllChildrenFolders(params: List<ChangeFolderParams>) =
        updateAllFolderChildren { childIdsByParentIds ->
            params.forEach { childIdsByParentIds.changeChildFolder(it) }
        }

    private fun ChildIdsByParentIds.changeChildFolder(
        params: ChangeFolderParams,
    ) {
        val fromChildIds = this[params.fromParentId] ?: return
        val toChildIds = this[params.toParentId] ?: return
        fromChildIds.removeAll { it.id == params.childId.toChildIdModel().id } // TODO why removeAll? test just with minus
        toChildIds += params.childId.toChildIdModel()
    }

    override suspend fun removeAllChildrenFromAllFolders() = realm.write {
        delete<FolderChildrenModel>()
    }
}