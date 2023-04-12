package ru.dimagor555.password.data.passwordsandfolderchildren

import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.first
import ru.dimagor555.password.data.password.PasswordModel
import ru.dimagor555.password.data.password.toPassword
import ru.dimagor555.password.data.password.toPasswordModel
import ru.dimagor555.password.data.password.update
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

class RealmBulkPasswordRepository(
    private val realm: Realm,
) : BulkPasswordRepository {

    override suspend fun getAll(): List<Password> = realm
        .query<PasswordModel>()
        .asFlow()
        .first()
        .list
        .map { it.toPassword() }

    override suspend fun addAll(passwords: List<Password>) = realm.write {
        passwords
            .map { it.toPasswordModel() }
            .forEach { copyToRealm(it) }
    }

    override suspend fun updateAll(passwords: List<Password>) = realm.write {
        val passwordIds = passwords.map { it.id!! }.toSet()
        val oldPasswords = getOldPasswords(passwordIds)
        val passwordsByIds = passwords.associateBy { it.id!! }
        oldPasswords
            .associateWith { passwordsByIds[it.id.toString()] }
            .filterValues { it != null }
            .forEach { (oldPassword, password) ->
                oldPassword.update(password!!)
            }
    }

    private fun MutableRealm.getOldPasswords(passwordIds: Set<String>): List<PasswordModel> =
        this
            .query<PasswordModel>()
            .find()
            .filter { it.id.toString() in passwordIds }

    override suspend fun removeAll() = realm.write {
        delete<PasswordModel>()
    }
}