package ru.dimagor555.passwordgeneration.domain

class CharGroupsSelection(
    selectedCharGroups: List<PasswordCharGroup>
) {
    private val selectedCharGroups = selectedCharGroups.toMutableSet()

    fun getSelectedGroups() = selectedCharGroups.toList()

    fun toggleCharGroup(charGroup: PasswordCharGroup) {
        if (charGroup in selectedCharGroups)
            selectedCharGroups -= charGroup
        else
            selectedCharGroups += charGroup
    }

    fun hasSelectedGroups() = selectedCharGroups.isNotEmpty()
}