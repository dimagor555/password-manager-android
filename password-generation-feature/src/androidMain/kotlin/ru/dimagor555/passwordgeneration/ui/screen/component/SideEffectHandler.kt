package ru.dimagor555.passwordgeneration.ui.screen.component

import android.content.Context
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.icerock.moko.resources.desc.StringDesc
import ru.dimagor555.mvicompose.android.OnSideEffect
import ru.dimagor555.passwordgeneration.ui.screen.PasswordGenerationViewModel
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore
import ru.dimagor555.ui.core.util.SnackbarMessage
import ru.dimagor555.ui.core.util.resolve

@Composable
internal fun SideEffectHandler(
    viewModel: PasswordGenerationViewModel,
    onShowSnackbar: (SnackbarMessage) -> Unit
) {
    val context = LocalContext.current
    OnSideEffect(viewModel) {
        when (it) {
            is PasswordGenerationStore.SideEffect.ShowMessage -> onShowSnackbar(
                createLongSnackbarMessage(it.message, context)
            )
        }
    }
}

private fun createLongSnackbarMessage(message: StringDesc, context: Context) =
    SnackbarMessage(
        message = message.resolve(),
        actionLabel = context.getString(android.R.string.ok),
        duration = SnackbarDuration.Long
    )