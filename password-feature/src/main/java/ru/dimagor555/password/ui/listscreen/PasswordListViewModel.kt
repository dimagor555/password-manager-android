package ru.dimagor555.password.ui.listscreen

import ru.dimagor555.mvicompose.android.MviViewModel
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.*
import ru.dimagor555.password.ui.listscreen.model.PasswordListUseCases

internal class PasswordListViewModel(
    useCases: PasswordListUseCases
) : MviViewModel<Action, State, SideEffect>(
    store = PasswordListStore(useCases = useCases)
)