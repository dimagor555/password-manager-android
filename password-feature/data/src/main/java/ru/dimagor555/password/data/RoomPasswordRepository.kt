package ru.dimagor555.password.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.dimagor555.password.data.dao.PasswordDao
import ru.dimagor555.password.data.model.PasswordEntity
import ru.dimagor555.password.data.model.toPassword
import ru.dimagor555.password.data.model.toPasswordModel
import ru.dimagor555.password.data.model.toPasswordUpdateEntity
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.PasswordRepository
import javax.inject.Inject

/*
 TODO: 04.12.2021 it's much suitable to inject whole database instead of dao.
      For example, in your dao you was forced to add @Transaction to all functions, but
      you really need it only for *update*. With data base class you will be able to
      use *runInTransaction* method
 */
internal class RoomPasswordRepository @Inject constructor(
    private val passwordDao: PasswordDao
) : PasswordRepository {

    override fun observeAll() =
        passwordDao.observeAll()
            .distinctUntilChanged()
            .map { it.map(PasswordEntity::toPassword) }
            .flowOn(Dispatchers.IO)
            .conflate()

    override fun observeById(id: Int) =
        passwordDao.observeById(id)
            .distinctUntilChanged()
            .map { it?.toPassword() }
            .flowOn(Dispatchers.IO)
            .conflate()

    override suspend fun getById(id: Int) = withContext(Dispatchers.IO) {
        passwordDao.getById(id)?.toPassword()
    }

    override suspend fun add(password: Password) = withContext(Dispatchers.IO) {
        passwordDao.insert(password.toPasswordModel())
    }

    override suspend fun update(password: Password) = withContext(Dispatchers.IO) {
        passwordDao.update(password.toPasswordUpdateEntity())
    }

    override suspend fun remove(passwordId: Int) = passwordDao.delete(passwordId)
}