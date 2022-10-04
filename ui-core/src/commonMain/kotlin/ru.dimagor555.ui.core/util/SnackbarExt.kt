package ru.dimagor555.ui.core.util

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.desc.StringDesc

data class SnackbarDescVisuals(
    val message: String,
    val actionLabel: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short
)

data class SnackbarDesc(
    val message: StringDesc,
    val actionLabel: StringDesc? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
)

@Composable
fun SnackbarDesc.resolve(): SnackbarDescVisuals {
    val desc = this
    return SnackbarDescVisuals(
        message = desc.message.resolve(),
        actionLabel = desc.actionLabel?.resolve(),
        duration = SnackbarDuration.Short,
    )
}

suspend fun SnackbarHostState.showSingleSnackbar(snackbarMessage: SnackbarDescVisuals) {
    currentSnackbarData?.dismiss()
    showSnackbar(snackbarMessage)
}

suspend fun SnackbarHostState.showSnackbar(snackbarMessage: SnackbarDescVisuals) {
    snackbarMessage.run { showSnackbar(message, actionLabel, duration) }
}