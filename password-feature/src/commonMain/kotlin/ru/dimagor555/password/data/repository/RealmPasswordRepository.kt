package ru.dimagor555.password.data.repository

import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.types.RealmSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.dimagor555.password.data.add
import ru.dimagor555.password.data.eqId
import ru.dimagor555.password.data.model.*
import ru.dimagor555.password.data.model.metadata.toPasswordMetadataModel
import ru.dimagor555.password.data.queryOneOrNull
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.usecase.password.repository.PasswordRepository

class RealmPasswordRepository(
    private val realm: Realm,
) : PasswordRepository {

    override fun observeById(id: String): Flow<Password?> = realm
        .query<PasswordModel>(PasswordModel::id eqId id)
        .first()
        .asFlow()
        .map { it.obj?.toPassword() }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .conflate()

    override fun observeAll(): Flow<List<Password>> = realm
        .query<PasswordModel>()
        .asFlow()
        .map { it.list.map(PasswordModel::toPassword) }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .conflate()

    override suspend fun getById(id: String): Password? = realm
        .queryOneOrNull<PasswordModel>(PasswordModel::id eqId id)
        ?.toPassword()

    override suspend fun getAllByIds(ids: Set<String>): List<Password> =
        getAll()
            .filter { it.id in ids }

    override suspend fun getAll(): List<Password> = realm
        .query<PasswordModel>()
        .asFlow()
        .first()
        .list
        .map { it.toPassword() }

    override suspend fun add(password: Password): String {
        val addedPassword = realm.add(password.toPasswordModel())
        return addedPassword.id.toString()
    }

    override suspend fun addAll(passwords: List<Password>) = realm.write {
        passwords
            .map { it.toPasswordModel() }
            .forEach { copyToRealm(it) }
    }

    override suspend fun update(password: Password) = realm.write {
        val oldPassword = this
            .query<PasswordModel>(Password::id eqId password.id!!)
            .first()
            .find()
            ?: return@write
        oldPassword.update(password)
    }

    private fun PasswordModel.update(password: Password) {
        metadata = password.metadata.toPasswordMetadataModel()
        fields = password.fields.toRealmSet()
    }

    private fun PasswordFields.toRealmSet(): RealmSet<FieldModel> =
        fields
            .map { it.toFieldModel() }
            .toRealmSet()

    override suspend fun updateAll(passwords: List<Password>) = realm.write {
        val passwordIds = passwords.map { it.id!! }
        val oldPasswords = realm
            .query<PasswordModel>()
            .find()
            .filter { it.id.toString() in passwordIds }
        val passwordsByIds = passwords.associateBy { it.id!! }
        oldPasswords
            .associateWith { passwordsByIds[it.id.toString()] }
            .filterValues { it != null }
            .forEach { (oldPassword, password) ->
                oldPassword.update(password!!)
            }
    }

    override suspend fun addOrUpdateAll(passwords: List<Password>) {
        val allPasswordIds = getAll().map { it.id!! }
        val (passwordsToUpdate, passwordsToAdd) = passwords.partition { it.id in allPasswordIds }
        coroutineScope {
            launch { addAll(passwordsToAdd) }
            launch { updateAll(passwordsToUpdate) }
        }
    }

    override suspend fun removeAllByIds(passwordIds: Set<String>) = realm.write {
        val passwords = realm
            .query<PasswordModel>()
            .find()
            .filter { it.id.toString() in passwordIds }
        passwords.forEach { delete(it) }
    }

    override suspend fun removeAll() = realm.write {
        delete<PasswordModel>()
    }
}
