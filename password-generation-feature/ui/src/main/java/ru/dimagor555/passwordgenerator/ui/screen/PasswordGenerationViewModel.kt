package ru.dimagor555.passwordgenerator.ui.screen

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.dimagor555.mvicompose.android.MviViewModel
import ru.dimagor555.passwordgenerator.ui.screen.model.PasswordGenerationStore
import ru.dimagor555.passwordgenerator.ui.screen.model.PasswordGenerationStore.*
import javax.inject.Inject

@HiltViewModel
internal class PasswordGenerationViewModel @Inject constructor() :
    MviViewModel<Action, State, SideEffect>(
        store = PasswordGenerationStore()
    )