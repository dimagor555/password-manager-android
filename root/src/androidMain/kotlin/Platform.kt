import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual class Platform actual constructor() {
    actual fun setupLogging() {
        Napier.base(DebugAntilog())
    }
}