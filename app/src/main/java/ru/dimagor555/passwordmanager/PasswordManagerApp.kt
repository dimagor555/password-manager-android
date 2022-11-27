package ru.dimagor555.passwordmanager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.dimagor555.encryption.android.di.androidEncryptionModule
import ru.dimagor555.encryption.di.encryptionModule
import ru.dimagor555.masterpassword.ui.di.masterPasswordUiModule
import ru.dimagor555.password.ui.di.passwordUiModule
import ru.dimagor555.passwordgeneration.ui.di.passwordGenerationUiModule

class PasswordManagerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PasswordManagerApp)
            modules(
                androidEncryptionModule,
                encryptionModule,
                passwordUiModule,
                passwordGenerationUiModule,
                masterPasswordUiModule
            )
        }
    }
}