package ru.dimagor555.password.ui.detailsscreen

import ru.dimagor555.mvicompose.android.MviViewModel
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.*
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsUseCases

internal class PasswordDetailsViewModel(
    useCases: PasswordDetailsUseCases
) : MviViewModel<Action, State, SideEffect>(
    store = PasswordDetailsStore(useCases)
)