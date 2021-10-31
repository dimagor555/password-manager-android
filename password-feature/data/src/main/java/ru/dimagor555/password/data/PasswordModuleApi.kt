package ru.dimagor555.password.data

import android.content.Context
import ru.dimagor555.password.data.dao.PasswordDao
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository

class PasswordModuleApi internal constructor(
    applicationContext: Context,
    passwordDao: PasswordDao
) {
    val clipboardRepository: ClipboardRepository = ClipboardRepositoryImpl(applicationContext)

    val passwordRepository: PasswordRepository = RoomPasswordRepository(passwordDao)
}