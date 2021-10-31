package ru.dimagor555.password.data

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.dimagor555.password.data.dao.PasswordDao
import ru.dimagor555.password.data.model.PasswordEntity
import ru.dimagor555.password.data.model.toPassword
import ru.dimagor555.password.data.model.toPasswordModel
import ru.dimagor555.password.data.model.toPasswordUpdateEntity
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.PasswordRepository

internal class RoomPasswordRepository(
    private val passwordDao: PasswordDao
) : PasswordRepository {
    override fun getAll() =
        passwordDao.getAll()
            .distinctUntilChanged()
            .map { it.map(PasswordEntity::toPassword) }

    override fun getById(id: Int) =
        passwordDao.getById(id)
            .distinctUntilChanged()
            .map(PasswordEntity::toPassword)

    override suspend fun add(password: Password) {
        passwordDao.insert(password.toPasswordModel())
    }

    override suspend fun update(password: Password) {
        passwordDao.update(password.toPasswordUpdateEntity())
    }

    override suspend fun remove(password: Password) {
        passwordDao.delete(passwordId = password.id!!)
    }
}