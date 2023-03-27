package ru.dimagor555.ui.core.util

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@DslMarker
annotation class ScreenSizeDsl

@ScreenSizeDsl
val LocalScreenSize = compositionLocalOf { ScreenSize.MOBILE }

@ScreenSizeDsl
enum class ScreenSize {
    MOBILE, DESKTOP
}

@ScreenSizeDsl
fun BoxWithConstraintsScope.determineWindowType(): ScreenSize =
    when {
        maxWidth <= 600.dp -> ScreenSize.MOBILE
        else -> ScreenSize.DESKTOP
    }

@ScreenSizeDsl
@Composable
infix fun <T> T.desktop(other: T): T =
    when (LocalScreenSize.current) {
        ScreenSize.MOBILE -> this
        ScreenSize.DESKTOP -> other
    }

@ScreenSizeDsl
@Composable
inline fun Modifier.onMobile(block: Modifier.() -> Modifier) =
    this.block() desktop this

@ScreenSizeDsl
@Composable
inline fun Modifier.onDesktop(block: Modifier.() -> Modifier) =
    this desktop this.block()