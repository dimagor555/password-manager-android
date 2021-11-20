package ru.dimagor555.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.dimagor555.password.listscreen.PasswordListScreen
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                PasswordListScreen(
                    navigateToPasswordDetailsScreen = {},
                    navigateToSettingsScreen = {},
                    navigateToPasswordCreationScreen = {}
                )
            }
        }
    }
}