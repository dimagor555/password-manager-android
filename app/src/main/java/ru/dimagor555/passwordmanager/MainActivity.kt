package ru.dimagor555.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.dimagor555.navigation.AppNavigationFlow
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var showSplashScreen by mutableStateOf(true)
        installSplashScreen().setKeepVisibleCondition { showSplashScreen }
        setContent {
            PasswordManagerTheme {
                AppNavigationFlow(onShowFirstScreen = { showSplashScreen = false })
            }
        }
    }
}