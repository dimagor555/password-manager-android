package ru.dimagor555.ui.core.util

import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun rememberThrottledFalseBoolean(
    newValue: Boolean,
    initialValue: Boolean = newValue,
    throttleDuration: Long = DEFAULT_BOOLEAN_THROTTLE_DURATION,
): Boolean = rememberThrottledBoolean(
    newValue = newValue,
    initialValue = initialValue,
    throttleOnValue = false,
    throttleDuration = throttleDuration,
)

@Composable
fun rememberThrottledTrueBoolean(
    newValue: Boolean,
    initialValue: Boolean = newValue,
    throttleDuration: Long = DEFAULT_BOOLEAN_THROTTLE_DURATION,
): Boolean = rememberThrottledBoolean(
    newValue = newValue,
    initialValue = initialValue,
    throttleOnValue = true,
    throttleDuration = throttleDuration,
)

@Composable
private fun rememberThrottledBoolean(
    newValue: Boolean,
    initialValue: Boolean,
    throttleOnValue: Boolean,
    throttleDuration: Long,
): Boolean {
    var value by remember { mutableStateOf(initialValue) }
    LaunchedEffect(newValue) {
        if (newValue == throttleOnValue) {
            delay(throttleDuration)
        }
        value = newValue
    }
    return value
}

private const val DEFAULT_BOOLEAN_THROTTLE_DURATION = 300L