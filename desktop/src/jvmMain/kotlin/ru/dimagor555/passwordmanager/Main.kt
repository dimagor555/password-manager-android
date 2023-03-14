import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.swing.Swing
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import ru.dimagor555.core.presentation.PasswordManagerRootComponent
import ru.dimagor555.core.presentation.PasswordManagerRootScreen
import ru.dimagor555.encryption.di.encryptionModule
import ru.dimagor555.masterpassword.ui.di.masterPasswordModule
import ru.dimagor555.password.di.passwordModule
import ru.dimagor555.passwordgeneration.ui.di.passwordGenerationUiModule
import ru.dimagor555.synchronization.di.synchronizationModule
import ru.dimagor555.syncpassintegration.di.syncPasswordIntegrationModule
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@OptIn(ExperimentalDecomposeApi::class, ExperimentalCoroutinesApi::class)
fun main() {
    Dispatchers.setMain(Dispatchers.Swing)

    Napier.base(DebugAntilog())
    startKoin {
        modules(
            encryptionModule,
            passwordModule,
            passwordGenerationUiModule,
            masterPasswordModule,
            synchronizationModule,
            syncPasswordIntegrationModule,
        )
    }

    val lifecycle = LifecycleRegistry()
    val root = PasswordManagerRootComponent(DefaultComponentContext(lifecycle))

    application {
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Password Manager",
        ) {
            PasswordManagerTheme {
                PasswordManagerRootScreen(
                    component = root,
                    onShowFirstScreen = { },
                )
            }
        }
    }
}
