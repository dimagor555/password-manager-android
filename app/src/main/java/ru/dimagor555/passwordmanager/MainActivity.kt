package ru.dimagor555.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.defaultComponentContext
import ru.dimagor555.core.presentation.PasswordManagerRootComponent
import ru.dimagor555.core.presentation.PasswordManagerRootScreen
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
