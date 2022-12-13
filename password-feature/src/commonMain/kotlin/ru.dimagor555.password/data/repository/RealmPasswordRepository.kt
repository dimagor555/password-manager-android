package ru.dimagor555.password.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.dimagor555.password.data.*
import ru.dimagor555.password.data.model.*
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.repository.PasswordRepository

class RealmPasswordRepository(
    private val realm: Realm,
) : PasswordRepository {

    override fun observeAll(): Flow<List<Password>> = realm
        .query<PasswordModel>()
        .asFlow()
        .map { it.list.map(PasswordModel::toPassword) }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .conflate()

    override fun observeById(id: String): Flow<Password?> = realm
        .query<PasswordModel>(PasswordModel::id eqId id)
        .first()
        .asFlow()
        .map { it.obj?.toPassword() }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .conflate()

    override suspend fun getById(id: String): Password =
        realm.getById<PasswordModel>(id).toPassword()

    override suspend fun add(password: Password): String =
        realm.addOrUpdate(password.toPasswordModel()).id.toString()

    override suspend fun update(password: Password) {
        realm.addOrUpdate(password.toPasswordModel())
    }

    override suspend fun remove(id: String) =
        realm.removeById<PasswordModel>(id)

    override suspend fun removeFolderPasswords(passwordIds: List<String>) =
        passwordIds.forEach {
            realm.removeById<FolderModel>(it)
        }
}
