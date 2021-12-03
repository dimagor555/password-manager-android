package ru.dimagor555.password.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.data.model.PasswordEntity
import ru.dimagor555.password.data.model.PasswordModel
import ru.dimagor555.password.data.model.PasswordUpdateEntity
import ru.dimagor555.password.data.model.UsageModel

@Dao
internal abstract class PasswordDao {
    @Transaction
    @Query("select * from PasswordModel")
    abstract fun observeAll(): Flow<List<PasswordEntity>>

    @Transaction
    @Query("select * from PasswordModel where id = :id")
    abstract fun observeById(id: Int): Flow<PasswordEntity?>

    @Transaction
    @Query("select * from PasswordModel where id = :id")
    abstract suspend fun getById(id: Int): PasswordEntity?

    @Insert
    abstract suspend fun insert(passwordModel: PasswordModel)

    @Transaction
    open suspend fun update(entity: PasswordUpdateEntity) {
        updatePasswordModel(entity.passwordModel)
        insertUsageModels(entity.usageModelsToInsert)
        updateUsageModels(entity.usageModelsToUpdate)
    }

    @Update
    protected abstract suspend fun updatePasswordModel(passwordModel: PasswordModel)

    @Insert
    protected abstract suspend fun insertUsageModels(usageModels: List<UsageModel>)

    @Update
    protected abstract suspend fun updateUsageModels(usageModels: List<UsageModel>)

    @Query("delete from PasswordModel where id = :passwordId")
    abstract suspend fun delete(passwordId: Int)
}