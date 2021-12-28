package ru.dimagor555.masterpassword.editscreen

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.State
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordUseCases
import ru.dimagor555.mvicompose.android.MviViewModel
import javax.inject.Inject

@HiltViewModel
internal class EditMasterPasswordViewModel @Inject constructor(
    useCases: EditMasterPasswordUseCases
) : MviViewModel<Action, State, Nothing>(
    store = EditMasterPasswordStore(useCases)
)
