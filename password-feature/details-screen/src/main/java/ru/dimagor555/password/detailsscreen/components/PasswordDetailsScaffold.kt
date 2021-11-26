package ru.dimagor555.password.detailsscreen.components

import androidx.compose.runtime.Composable
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsEvent
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsEvent.ToggleFavourite
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsEvent.UpdateRemoveDialogVisibility
import ru.dimagor555.password.detailsscreen.model.PasswordViewState
import ru.dimagor555.ui.core.components.SingleSnackbarSimpleScaffold

@Composable
internal fun PasswordDetailsScaffold(
    passwordState: PasswordViewState,
    sendEvent: (PasswordDetailsEvent) -> Unit,
    onNavigateBack: () -> Unit,
    navigateToPasswordEditingScreen: () -> Unit,
    content: @Composable (onShowSnackBar: (String, String?) -> Unit) -> Unit
) {
    SingleSnackbarSimpleScaffold(
        topAppBar = {
            PasswordDetailsTopAppBar(
                title = passwordState.title,
                onRemovePasswordClicked = {
                    sendEvent(UpdateRemoveDialogVisibility(UiComponentVisibility.Show))
                },
                navigateBack = onNavigateBack,
                navigateToPasswordEditingScreen = navigateToPasswordEditingScreen
            )
        },
        floatingActionButton = {
            FavouriteFloatingActionButton(
                isFavourite = passwordState.isFavourite,
                onToggleFavourite = { sendEvent(ToggleFavourite) }
            )
        }
    ) { _, onShowSnackbar ->
        content(onShowSnackbar)
    }
}