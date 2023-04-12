package ru.dimagor555.ui.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import ru.dimagor555.ui.core.util.LocalScreenSize
import ru.dimagor555.ui.core.util.determineWindowType

private val DarkColorPalette = darkColors(
    primary = Orange400,
    primaryVariant = Orange800,
    secondary = Blue400,
    secondaryVariant = Blue500
)

private val LightColorPalette = lightColors(
    primary = Orange200,
    primaryVariant = Orange500,
    secondary = Blue300,
    secondaryVariant = Blue400,
    onPrimary = Color.Black
)

@Composable
fun PasswordManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    // TODO extract
    BoxWithConstraints {
        CompositionLocalProvider(LocalScreenSize provides determineWindowType()) {
            MaterialTheme(
                colors = colors,
                typography = MontserratTypography,
                shapes = Shapes,
                content = content
            )
        }
    }
}