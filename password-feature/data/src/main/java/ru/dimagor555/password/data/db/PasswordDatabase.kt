package ru.dimagor555.password.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dimagor555.password.data.dao.PasswordModelDao
import ru.dimagor555.password.data.model.PasswordModel

@Database(
    entities = [PasswordModel::class],
    version = 1,
    exportSchema = false
)
internal abstract class PasswordDatabase : RoomDatabase() {
    abstract fun passwordModelDao(): PasswordModelDao

    companion object {
        const val DATABASE_NAME = "password_feature_database"
    }
}