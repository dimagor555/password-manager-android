package ru.dimagor555.password.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.types.RealmObject
import ru.dimagor555.password.data.*
import ru.dimagor555.password.data.model.*
import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.repository.FolderChildrenRepository

class RealmFolderChildrenRepository(
    private val realm: Realm,
) : FolderChildrenRepository {

    override suspend fun getById(id: String): FolderChildren =
        realm
            .getById<FolderChildrenModel>(id)
            .toFolderChildren()

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

    override suspend fun update(folder: FolderChildren) {
        realm.addOrUpdate(folder.toFolderChildrenModel())
    }

    override suspend fun replaceChildLocation(id: String, fromId: String, toId: String) {
        removeChildFromFolderChildren(fromId, ChildId.PasswordId(id))
        addChildToFolderChildren(toId, ChildId.PasswordId(id))
    }

    override suspend fun <T : ChildId> removeChildFromFolderChildren(
        parentId: String,
        childId: T,
    ) {
        realm.write {
            val folderChildrenModel = realm.getById<FolderChildrenModel>(parentId, "parentId")
            folderChildrenModel.childrenIds =
                (folderChildrenModel.childrenIds!! + childId.toChildIdModel()).toRealmSet()
        }
    }

    override suspend fun <T : ChildId> addChildToFolderChildren(
        parentId: String,
        childId: T,
    ) {
        realm.addOrUpdate(FolderChildren(parentId, setOf<ChildId>(childId)).toFolderChildrenModel())
    }

    override suspend fun remove(id: String) {
        realm.removeById<FolderChildrenModel>(id, "parentId")
    }
}