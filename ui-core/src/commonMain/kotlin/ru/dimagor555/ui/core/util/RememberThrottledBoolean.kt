package ru.dimagor555.ui.core.util

import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun rememberThrottledFalseBoolean(
    newValue: Boolean,
    throttleDuration: Long = DEFAULT_BOOLEAN_THROTTLE_DURATION,
): Boolean = rememberThrottledBoolean(
    newValue = newValue,
    throttleOnValue = false,
    throttleDuration = throttleDuration,
)

@Composable
fun rememberThrottledTrueBoolean(
    newValue: Boolean,
    throttleDuration: Long = DEFAULT_BOOLEAN_THROTTLE_DURATION,
): Boolean = rememberThrottledBoolean(
    newValue = newValue,
    throttleOnValue = true,
    throttleDuration = throttleDuration,
)

@Composable
private fun rememberThrottledBoolean(
    newValue: Boolean,
    throttleOnValue: Boolean,
    throttleDuration: Long,
): Boolean {
    var value by remember { mutableStateOf(newValue) }
    LaunchedEffect(newValue) {
        if (newValue == throttleOnValue) {
            delay(throttleDuration)
        }
        value = newValue
    }
    return value
}

private const val DEFAULT_BOOLEAN_THROTTLE_DURATION = 300L