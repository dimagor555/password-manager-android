package ru.dimagor555.password.data.repository

import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.dimagor555.password.data.*
import ru.dimagor555.password.data.model.*
import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParams
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildParams
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildrenRepository

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
        realm.add(folderChildrenModel.toFolderChildrenModel())
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

    override suspend fun changeChildFolder(params: ChangeFolderParams) = params.run {
        removeChildFromFolder(FolderChildParams(fromParentId, childId))
        addChildToFolder(FolderChildParams(toParentId, childId))
    }

    override suspend fun addChildToFolder(params: FolderChildParams) = params.run {
        realm.write {
            val oldFolderChildren = getFolderChildrenModelOrNull(parentId) ?: return@write
            val newChildIds = oldFolderChildren.childrenIds!! + childId.toChildIdModel()
            oldFolderChildren.childrenIds = newChildIds.toRealmSet()
        }
    }

    private fun MutableRealm.getFolderChildrenModelOrNull(parentId: String): FolderChildrenModel? =
        this
            .query<FolderChildrenModel>(FolderChildrenModel::parentId eqId parentId)
            .first()
            .find()

    override suspend fun removeChildFromFolder(params: FolderChildParams) = params.run {
        realm.write {
            val oldFolderChildren = getFolderChildrenModelOrNull(parentId) ?: return@write
            val newChildIds = oldFolderChildren
                .childrenIds!!
                .filter { it.id == childId.toChildIdModel().id } // TODO why filter? test just with minus
            oldFolderChildren.childrenIds = newChildIds.toRealmSet()
        }
    }

    override suspend fun remove(id: String) {
        realm.removeById<FolderChildrenModel>(id, "parentId")
    }
}
