import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import org.koin.core.context.startKoin
import ru.dimagor555.core.presentation.PasswordManagerRootComponent
import ru.dimagor555.core.presentation.PasswordManagerRootScreen
import ru.dimagor555.encryption.symmetric.di.symmetricEncryptionModule
import ru.dimagor555.encryption.asymmetric.di.asymmetricEncryptionModule
import ru.dimagor555.export.di.exportModule
import ru.dimagor555.export.integration.di.exportIntegrationModule
import ru.dimagor555.masterpassword.di.masterPasswordModule
import ru.dimagor555.password.di.passwordModule
import ru.dimagor555.passwordgeneration.di.passwordGenerationModule
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    Napier.base(DebugAntilog())
    startKoin {
        modules(
            symmetricEncryptionModule,
            asymmetricEncryptionModule,
            passwordModule,
            passwordGenerationModule,
            masterPasswordModule,
            exportModule,
            exportIntegrationModule,
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
