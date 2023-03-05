package ru.dimagor555.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.dimagor555.core.presentation.PasswordManagerRootComponent
import ru.dimagor555.core.presentation.PasswordManagerRootScreen
import ru.dimagor555.synchronization.data.SendPasswordApiImpl
import ru.dimagor555.synchronization.repository.ClientRepository
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val clientRepository: ClientRepository by inject()
            clientRepository.findSyncDevices()

            val sendPasswordApiImpl: SendPasswordApiImpl by inject()
            val syncPasswords = sendPasswordApiImpl.postSyncPasswordRecord()
            sendPasswordApiImpl.addPasswords(syncPasswords)
            sendPasswordApiImpl.postRequestPasswords(syncPasswords)
        }

        var showSplashScreen by mutableStateOf(true)
        installSplashScreen().setKeepOnScreenCondition { showSplashScreen }
        val root = PasswordManagerRootComponent(defaultComponentContext())
        setContent {
            PasswordManagerTheme {
                PasswordManagerRootScreen(
                    component = root,
                    onShowFirstScreen = { showSplashScreen = false },
                )
            }
        }
    }
}
