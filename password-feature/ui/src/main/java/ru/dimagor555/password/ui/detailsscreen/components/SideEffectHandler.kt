package ru.dimagor555.password.ui.detailsscreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.dimagor555.mvicompose.android.OnSideEffect
import ru.dimagor555.password.ui.detailsscreen.PasswordDetailsViewModel
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.SideEffect
import ru.dimagor555.ui.core.util.SnackbarMessage

@Composable
internal fun SideEffectHandler(
    viewModel: PasswordDetailsViewModel,
    onShowSnackbar: (SnackbarMessage) -> Unit
) {
    val context = LocalContext.current
    OnSideEffect(viewModel) {
        when(it) {
            is SideEffect.ShowMessage -> onShowSnackbar(
                SnackbarMessage(
                    message = it.message.resolve(context) as String,
                    actionLabel = context.getString(android.R.string.ok)
                )
            )
        }
    }
}