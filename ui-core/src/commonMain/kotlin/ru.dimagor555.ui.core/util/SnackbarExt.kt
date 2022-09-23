package ru.dimagor555.ui.core.util

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState

data class SnackbarMessage(
    val message: String,
    val actionLabel: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short
)

suspend fun SnackbarHostState.showSingleSnackbar(snackbarMessage: SnackbarMessage) {
    currentSnackbarData?.dismiss()
    showSnackbar(snackbarMessage)
}

suspend fun SnackbarHostState.showSnackbar(snackbarMessage: SnackbarMessage) {
    snackbarMessage.run { showSnackbar(message, actionLabel, duration) }
}