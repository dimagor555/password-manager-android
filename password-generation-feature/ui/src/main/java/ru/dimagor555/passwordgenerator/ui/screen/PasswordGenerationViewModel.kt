package ru.dimagor555.passwordgenerator.ui.screen

import ru.dimagor555.mvicompose.android.MviViewModel
import ru.dimagor555.passwordgenerator.ui.screen.model.PasswordGenerationStore
import ru.dimagor555.passwordgenerator.ui.screen.model.PasswordGenerationStore.*

internal class PasswordGenerationViewModel() :
    MviViewModel<Action, State, SideEffect>(
        store = PasswordGenerationStore()
    )