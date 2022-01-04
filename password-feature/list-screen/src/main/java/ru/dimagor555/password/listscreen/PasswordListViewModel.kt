package ru.dimagor555.password.listscreen

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.dimagor555.mvicompose.android.MviViewModel
import ru.dimagor555.password.listscreen.model.PasswordListStore
import ru.dimagor555.password.listscreen.model.PasswordListStore.*
import ru.dimagor555.password.listscreen.model.PasswordListUseCases
import javax.inject.Inject

@HiltViewModel
internal class PasswordListViewModel @Inject constructor(
    useCases: PasswordListUseCases
) : MviViewModel<Action, State, SideEffect>(
    store = PasswordListStore(useCases = useCases)
)