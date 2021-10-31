package ru.dimagor555.password.data

import android.content.Context
import androidx.room.Room
import ru.dimagor555.password.data.db.PasswordDatabase

object PasswordModuleHolder {
    private var database: PasswordDatabase? = null
    private var passwordModuleApi: PasswordModuleApi? = null

    fun get() = requireNotNull(passwordModuleApi) { "Module is not initialized" }

    fun init(applicationContext: Context) = synchronized(this) {
        if (database == null)
            initDatabase(applicationContext)
        passwordModuleApi = PasswordModuleApi(applicationContext, database!!.passwordDao())
    }

    private fun initDatabase(applicationContext: Context) {
        database = Room.databaseBuilder(
            applicationContext,
            PasswordDatabase::class.java,
            PasswordDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }
}