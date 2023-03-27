package ru.dimagor555.ui.core.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.dimagor555.ui.core.util.desktop
import ru.dimagor555.ui.core.util.onDesktop

@Composable
fun MultiplatformContainer(
    outerBoxModifier: Modifier = Modifier,
    innerBoxModifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopCenter desktop Alignment.Center,
    desktopContainerWidth: Dp = DEFAULT_DESKTOP_CONTAINER_WIDTH,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = outerBoxModifier.fillMaxSize(),
        contentAlignment = contentAlignment,
    ) {
        Box(
            modifier = innerBoxModifier.onDesktop { widthIn(max = desktopContainerWidth) },
            content = content,
        )
    }
}

private val DEFAULT_DESKTOP_CONTAINER_WIDTH = 500.dp