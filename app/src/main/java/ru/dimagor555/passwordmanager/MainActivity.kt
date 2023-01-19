package ru.dimagor555.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import ru.dimagor555.core.presentation.PasswordManagerRootComponent
import ru.dimagor555.core.presentation.PasswordManagerRootScreen
import ru.dimagor555.core.presentation.RootComponent
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var showSplashScreen by mutableStateOf(true)
        installSplashScreen().setKeepVisibleCondition { showSplashScreen }

        Platform().setupLogging()
        val root = passwordManagerRoot(defaultComponentContext())

        setContent {
            PasswordManagerTheme {
                PasswordManagerRootScreen(root)
            }
        }
    }

    private fun passwordManagerRoot(componentContext: ComponentContext): RootComponent =
        PasswordManagerRootComponent(componentContext = componentContext)
}
