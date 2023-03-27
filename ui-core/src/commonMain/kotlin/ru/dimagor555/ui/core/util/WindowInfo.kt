package ru.dimagor555.ui.core.util

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getWindowType(boxWithConstraintsScope: BoxWithConstraintsScope): WindowType = when {
    boxWithConstraintsScope.maxWidth <= 600.dp -> WindowType.SMALL
    else -> WindowType.LARGE
}

enum class WindowType {
    SMALL, LARGE
}

@Composable
fun getWindowContentWidthModifier(
    boxWithConstraintsScope: BoxWithConstraintsScope,
    coefficient: Float = 1.75F,
): Modifier {
    val windowType = getWindowType(boxWithConstraintsScope)
    return if (windowType == WindowType.SMALL) {
        Modifier
    } else {
        Modifier.width(getWindowContentWidth(boxWithConstraintsScope, coefficient))
    }
}

fun getWindowContentWidth(
    boxWithConstraintsScope: BoxWithConstraintsScope,
    coefficient: Float,
): Dp {
    val width = boxWithConstraintsScope.maxWidth / coefficient
    return if (width <= 600.dp) {
        600.dp
    } else {
        width
    }
}