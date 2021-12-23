package ru.dimagor555.password.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.dimagor555.password.data.dao.PasswordModelDao
import ru.dimagor555.password.data.model.PasswordModel
import ru.dimagor555.password.data.model.toPassword
import ru.dimagor555.password.data.model.toPasswordModel
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.PasswordRepository

internal class RoomPasswordRepository(
    private val passwordModelDao: PasswordModelDao
) : PasswordRepository {
    override fun observeAll() =
        passwordModelDao.observeAll()
            .distinctUntilChanged()
            .map { it.map(PasswordModel::toPassword) }
            .flowOn(Dispatchers.IO)
            .conflate()

    override fun observeById(id: Int) =
        passwordModelDao.observeById(id)
            .distinctUntilChanged()
            .map { it?.toPassword() }
            .flowOn(Dispatchers.IO)
            .conflate()

    override suspend fun getById(id: Int) = withContext(Dispatchers.IO) {
        passwordModelDao.getById(id)?.toPassword()
    }

    override suspend fun add(password: Password) = withContext(Dispatchers.IO) {
        passwordModelDao.insert(password.toPasswordModel())
    }

    override suspend fun update(password: Password) = withContext(Dispatchers.IO) {
        passwordModelDao.update(password.toPasswordModel())
    }

    override suspend fun remove(passwordId: Int) = passwordModelDao.delete(passwordId)
}