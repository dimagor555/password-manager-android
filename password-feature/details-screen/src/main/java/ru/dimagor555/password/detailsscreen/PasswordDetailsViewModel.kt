package ru.dimagor555.password.detailsscreen

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.dimagor555.mvicompose.android.MviViewModel
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsStore
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsStore.*
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsUseCases
import javax.inject.Inject

@HiltViewModel
internal class PasswordDetailsViewModel @Inject constructor(
    useCases: PasswordDetailsUseCases
) : MviViewModel<Action, State, SideEffect>(
    store = PasswordDetailsStore(useCases)
)