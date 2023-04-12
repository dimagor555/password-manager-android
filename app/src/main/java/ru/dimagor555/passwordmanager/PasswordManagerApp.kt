package ru.dimagor555.passwordmanager

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.dimagor555.backup.di.backupModule
import ru.dimagor555.encryption.symmetric.di.symmetricEncryptionModule
import ru.dimagor555.encryption.asymmetric.di.asymmetricEncryptionModule
import ru.dimagor555.export.di.exportModule
import ru.dimagor555.export.integration.di.exportIntegrationModule
import ru.dimagor555.hashing.di.hashingModule
import ru.dimagor555.masterpassword.di.masterPasswordModule
import ru.dimagor555.password.di.passwordModule
import ru.dimagor555.passwordgeneration.di.passwordGenerationModule
import ru.dimagor555.synchronization.di.synchronizationModule
import ru.dimagor555.syncpassintegration.di.syncPasswordIntegrationModule

class PasswordManagerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        startKoin {
            androidContext(this@PasswordManagerApp)
            androidLogger()
            modules(
                symmetricEncryptionModule,
                hashingModule,
                asymmetricEncryptionModule,
                passwordModule,
                passwordGenerationModule,
                masterPasswordModule,
                exportModule,
                exportIntegrationModule,
                backupModule,
                synchronizationModule,
                syncPasswordIntegrationModule,
            )
        }
    }
}