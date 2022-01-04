package ru.dimagor555.password.listscreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.dimagor555.mvicompose.android.OnSideEffect
import ru.dimagor555.password.listscreen.PasswordListViewModel
import ru.dimagor555.password.listscreen.model.PasswordListStore
import ru.dimagor555.ui.core.util.SnackbarMessage

@Composable
internal fun SideEffectHandler(
    viewModel: PasswordListViewModel,
    onShowSnackbar: (SnackbarMessage) -> Unit
) {
    val context = LocalContext.current
    OnSideEffect(viewModel) {
        when (it) {
            is PasswordListStore.SideEffect.ShowMessage -> onShowSnackbar(
                SnackbarMessage(
                    message = it.message.resolve(context) as String,
                    actionLabel = context.getString(android.R.string.ok)
                )
            )
        }
    }
}