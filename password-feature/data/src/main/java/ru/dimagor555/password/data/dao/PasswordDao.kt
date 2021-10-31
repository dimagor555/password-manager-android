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
    abstract fun getAll(): Flow<List<PasswordEntity>>

    @Transaction
    @Query("select * from PasswordModel where id = :id")
    abstract fun getById(id: Int): Flow<PasswordEntity>

    @Insert
    abstract fun insert(passwordModel: PasswordModel)

    @Transaction
    open fun update(entity: PasswordUpdateEntity) {
        updatePasswordModel(entity.passwordModel)
        insertUsageModels(entity.usageModelsToInsert)
        updateUsageModels(entity.usageModelsToUpdate)
    }

    @Update
    protected abstract fun updatePasswordModel(passwordModel: PasswordModel)

    @Insert
    protected abstract fun insertUsageModels(usageModels: List<UsageModel>)

    @Update
    protected abstract fun updateUsageModels(usageModels: List<UsageModel>)

    @Query("delete from PasswordModel where id = :passwordId")
    abstract fun delete(passwordId: Int)
}