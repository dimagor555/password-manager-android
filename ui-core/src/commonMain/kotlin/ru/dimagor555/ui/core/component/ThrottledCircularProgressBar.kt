package ru.dimagor555.ui.core.component

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import ru.dimagor555.ui.core.util.rememberThrottledTrueBoolean

@Composable
fun ThrottledCircularProgressBar(
    visible: Boolean,
) {
    val isVisible = rememberThrottledTrueBoolean(
        newValue = visible,
        initialValue = false,
        throttleDuration = LOADER_THROTTLE_DURATION,
    )
    if (isVisible) {
        CircularProgressIndicator()
    }
}

private const val LOADER_THROTTLE_DURATION = 500L