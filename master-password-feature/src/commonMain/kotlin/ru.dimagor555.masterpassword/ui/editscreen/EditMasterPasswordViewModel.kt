package ru.dimagor555.masterpassword.ui.editscreen

import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.State
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordUseCases
import ru.dimagor555.mvicompose.android.MviViewModel

internal class EditMasterPasswordViewModel(
    useCases: EditMasterPasswordUseCases
) : MviViewModel<Action, State, Nothing>(
    store = EditMasterPasswordStore(useCases)
)
