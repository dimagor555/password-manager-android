package ru.dimagor555.passwordmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Orange500,
    primaryVariant = Orange800,
    secondary = Blue600
)

private val LightColorPalette = lightColors(
    primary = Orange200,
    primaryVariant = Orange500,
    secondary = Blue300,
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

    MaterialTheme(
        colors = colors,
        typography = MontserratTypography,
        shapes = Shapes,
        content = content
    )
}