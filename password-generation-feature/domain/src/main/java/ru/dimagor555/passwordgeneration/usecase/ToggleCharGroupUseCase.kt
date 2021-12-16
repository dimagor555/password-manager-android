package ru.dimagor555.passwordgeneration.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.passwordgeneration.domain.CharGroupsSelection
import ru.dimagor555.passwordgeneration.domain.PasswordCharGroup

class ToggleCharGroupUseCase {
    suspend operator fun invoke(
        selectedGroups: List<PasswordCharGroup>,
        groupToToggle: PasswordCharGroup
    ) = withContext(Dispatchers.Default) {
        CharGroupsSelection(selectedGroups)
            .apply { toggleCharGroup(groupToToggle) }
            .getSelectedGroups()
    }
}