package ru.dimagor555.password.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dimagor555.password.data.dao.PasswordDao
import ru.dimagor555.password.data.model.PasswordModel
import ru.dimagor555.password.data.model.UsageModel

@Database(
    entities = [
        PasswordModel::class,
        UsageModel::class
    ],
    version = 1
)
internal abstract class PasswordDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao

    companion object {
        const val DATABASE_NAME = "password_feature_database"
    }
}