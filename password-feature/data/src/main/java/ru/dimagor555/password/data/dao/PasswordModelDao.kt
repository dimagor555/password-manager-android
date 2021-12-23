package ru.dimagor555.password.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.data.model.PasswordModel

@Dao
internal interface PasswordModelDao {
    @Query("select * from password_model")
    fun observeAll(): Flow<List<PasswordModel>>

    @Query("select * from password_model where id = :id")
    fun observeById(id: Int): Flow<PasswordModel?>

    @Query("select * from password_model where id = :id")
    suspend fun getById(id: Int): PasswordModel?

    @Insert
    suspend fun insert(passwordModel: PasswordModel)

    @Update
    suspend fun update(passwordModel: PasswordModel)

    @Query("delete from password_model where id = :passwordId")
    suspend fun delete(passwordId: Int)
}