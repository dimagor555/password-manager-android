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

class RealmBulkFolderChildrenRepository(
    private val realm: Realm,
) : BulkFolderChildrenRepository {

    override suspend fun getAll(): List<FolderChildren> = realm
        .query<FolderChildrenModel>()
        .asFlow()
        .first()
        .list
        .map { it.toFolderChildren() }

    override suspend fun addAllChildrenToFolders(params: List<FolderChildParams>) = realm.write {
        val allFolderChildren = this.query<FolderChildrenModel>().find()
        params
            .groupBy { it.parentId }
            .mapValues { entry ->
                entry
                    .value
                    .map { it.childId.toChildIdModel() }
            }
            .updateInto(allFolderChildren)
    }

    private fun Map<String, Iterable<ChildIdModel>>.updateInto(
        folderChildren: RealmResults<FolderChildrenModel>,
    ) = this.forEach { (parentId, childIds) ->
        folderChildren
            .find { it.parentId.toString() == parentId }
            ?.childrenIds = childIds.toRealmSet()
    }

    override suspend fun changeAllChildrenFolders(params: List<ChangeFolderParams>) = realm.write {
        val allFolderChildren = this.query<FolderChildrenModel>().find()
        val childIdsByParentIds = allFolderChildren
            .associateBy { it.parentId.toString() }
            .mapValues { it.value.childrenIds!!.toMutableSet() }
        params.forEach { childIdsByParentIds.changeChildFolder(it) }
        childIdsByParentIds.updateInto(allFolderChildren)
    }

    private fun Map<String, MutableSet<ChildIdModel>>.changeChildFolder(
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