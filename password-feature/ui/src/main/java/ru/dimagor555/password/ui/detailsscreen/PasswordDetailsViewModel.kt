package ru.dimagor555.password.ui.detailsscreen

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.dimagor555.mvicompose.android.MviViewModel
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.*
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsUseCases
import javax.inject.Inject

@HiltViewModel
internal class PasswordDetailsViewModel @Inject constructor(
    useCases: PasswordDetailsUseCases
) : MviViewModel<Action, State, SideEffect>(
    store = PasswordDetailsStore(useCases)
)