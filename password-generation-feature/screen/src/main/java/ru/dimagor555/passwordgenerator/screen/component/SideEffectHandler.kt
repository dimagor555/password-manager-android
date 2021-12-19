package ru.dimagor555.passwordgenerator.screen.component

import android.content.Context
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.mvicompose.android.OnSideEffect
import ru.dimagor555.passwordgenerator.screen.PasswordGenerationViewModel
import ru.dimagor555.passwordgenerator.screen.model.PasswordGenerationStore
import ru.dimagor555.ui.core.util.SnackbarMessage

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

private fun createLongSnackbarMessage(message: LocalizedString, context: Context) =
    SnackbarMessage(
        message = message.resolve(context) as String,
        actionLabel = context.getString(android.R.string.ok),
        duration = SnackbarDuration.Long
    )