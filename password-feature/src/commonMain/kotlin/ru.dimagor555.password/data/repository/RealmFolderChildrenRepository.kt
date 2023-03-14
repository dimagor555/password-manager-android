package ru.dimagor555.password.data.repository

import io.github.aakira.napier.Napier
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.dimagor555.password.data.*
import ru.dimagor555.password.data.model.*
import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.repository.FolderChildrenRepository

class RealmFolderChildrenRepository(
    private val realm: Realm,
) : FolderChildrenRepository {

    override fun getById(id: String): FolderChildren =
        realm
            .getById<FolderChildrenModel>(id)
            .toFolderChildren()

    override suspend fun observeById(parentId: String): Flow<FolderChildren?> = realm
        .query<FolderChildrenModel>(FolderChildrenModel::parentId eqId parentId)
        .first()
        .asFlow()
        .map { it.obj?.toFolderChildren() }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .conflate()

    override fun getChildObjects(id: String): Set<Child> {
        val folderChildrenModel = realm.getById<FolderChildrenModel>(id, "parentId")
        return folderChildrenModel.childrenIds!!.map {
            when (it.type) {
                ChildIdType.FOLDER -> realm
                    .query<FolderModel>(FolderModel::id eqId it.id)
                    .map()
                ChildIdType.PASSWORD -> realm
                    .query<PasswordModel>(PasswordModel::id eqId it.id)
                    .map()
            }
        }.toChildren()
    }

    private fun List<RealmObject>.toChildren(): Set<Child> =
        map {
            when (it) {
                is PasswordModel -> it.toPassword()
                is FolderModel -> it.toFolderDescriptor()
                else -> error("unknown child type")
            }
        }.toSet()

    override fun getParentId(id: String): String {
        val folderChildrenModel = realm
            .query<FolderChildrenModel>("ANY childrenIds.id == id")
            .map()
        return folderChildrenModel.parentId.toString()
    }

    override fun getFolderPasswords(id: String): List<String> {
        val childrenModel = realm.getById<FolderChildrenModel>(id)
        return getFolderChildren(childrenModel, ChildIdType.PASSWORD)
    }

    override fun getFolderFolders(id: String): List<String> {
        val childrenModel = realm.getById<FolderChildrenModel>(id)
        return getFolderChildren(childrenModel, ChildIdType.FOLDER)
    }

    private fun getFolderChildren(childrenModel: FolderChildrenModel, childIdType: ChildIdType) =
        childrenModel.childrenIds!!
            .filter { it.type == childIdType }
            .map { it.id.toString() }

    override suspend fun add(folderChildrenModel: FolderChildren) {
        realm.addOrUpdate(folderChildrenModel.toFolderChildrenModel())
    }

    override suspend fun update(folderChildren: FolderChildren) {
        realm.write {
            val oldFolderChildren =
                this.query<FolderChildrenModel>("parentId == uuid(${folderChildren.parentId})")
                    .first().find()
            if (oldFolderChildren != null) {
                oldFolderChildren.childrenIds = folderChildren.childrenIds.map {
                    it.toChildIdModel()
                }.toRealmSet()
            }
        }
    }

    override suspend fun replaceChildLocation(id: String, fromId: String, toId: String) {
        Napier.e("replaceChildLocation() id = $id, fromId = $fromId, toId = $toId")
        removeChildFromFolderChildren(fromId, ChildId.PasswordId(id))
        addChildToFolderChildren(toId, ChildId.PasswordId(id))
    }

    override suspend fun <T : ChildId> removeChildFromFolderChildren(
        parentId: String,
        childId: T,
    ) {
        realm.write {
            val oldFolderChildren =
                this.query<FolderChildrenModel>("parentId == uuid($parentId)").first().find()
            if (oldFolderChildren != null) {
                val childrenIds = oldFolderChildren.childrenIds!!.toMutableSet()
                val newChildrenIds = childrenIds.filter { it.id != childId.toChildIdModel().id }.toSet()
                oldFolderChildren.childrenIds = newChildrenIds.toRealmSet()
            }
        }
    }

    override suspend fun <T : ChildId> addChildToFolderChildren(
        parentId: String,
        childId: T,
    ) {
        realm.write {
            val oldFolderChildren =
                this.query<FolderChildrenModel>("parentId == uuid($parentId)").first().find()
            if (oldFolderChildren != null) {
                val childrenIds = oldFolderChildren.childrenIds!!.toMutableSet()
                childrenIds.add(childId.toChildIdModel())
                oldFolderChildren.childrenIds = childrenIds.toRealmSet()
            }
        }
    }

    override suspend fun remove(id: String) {
        realm.removeById<FolderChildrenModel>(id, "parentId")
    }
}
