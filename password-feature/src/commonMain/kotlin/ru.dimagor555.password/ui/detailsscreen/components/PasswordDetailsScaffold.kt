package ru.dimagor555.password.ui.detailsscreen.components

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.Action
import ru.dimagor555.password.ui.detailsscreen.model.PasswordState
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold

@Composable
internal fun PasswordDetailsScaffold(
    passwordState: PasswordState,
    sendAction: (Action) -> Unit,
    onNavigateBack: () -> Unit,
    navigateToPasswordEditingScreen: () -> Unit,
    content: @Composable (snackbarHostState: SnackbarHostState) -> Unit
) {
    SingleSnackbarScaffold(
        topBar = {
            PasswordDetailsTopAppBar(
                title = passwordState.title,
                onRemovePasswordClicked = {
                    sendAction(Action.ChangeRemoveDialogVisibility(visible = true))
                },
                navigateBack = onNavigateBack,
                navigateToPasswordEditingScreen = navigateToPasswordEditingScreen
            )
        },
        floatingActionButton = {
            FavouriteFloatingActionButton(
                isFavourite = passwordState.isFavourite,
                onToggleFavourite = { sendAction(Action.ToggleFavourite) }
            )
        }
    ) { _, onShowSnackbar ->
        content(onShowSnackbar)
    }
}