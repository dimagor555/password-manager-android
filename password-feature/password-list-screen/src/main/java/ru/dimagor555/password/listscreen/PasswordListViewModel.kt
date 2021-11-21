package ru.dimagor555.password.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.dimagor555.core.DataState
import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.PasswordSortingType
import ru.dimagor555.password.listscreen.model.*
import javax.inject.Inject

@HiltViewModel
internal class PasswordListViewModel @Inject constructor(
    private val useCases: PasswordListUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(PasswordListViewState())
    val state = _state.asStateFlow()

    private val passwords = MutableStateFlow(emptyList<Password>())

    init {
        observePasswords()
    }

    private fun observePasswords() {
        useCases.observePasswords().onEach {
            when (it) {
                is DataState.Data -> {
                    passwords.value = it.data
                    onTriggerEvent(PasswordListEvent.UpdateProgressBarState(ProgressBarState.Idle))
                    onTriggerEvent(PasswordListEvent.FilterAndSortPasswords)
                }
                is DataState.Loading -> {
                    onTriggerEvent(PasswordListEvent.UpdateProgressBarState(ProgressBarState.Loading))
                }
                else -> error("Illegal data state")
            }
        }.launchIn(viewModelScope)
    }

    fun onTriggerEvent(event: PasswordListEvent) {
        when (event) {
            PasswordListEvent.FilterAndSortPasswords -> {
                filterAndSortPasswords()
            }
            is PasswordListEvent.UpdateProgressBarState -> {
                _state.update { it.copy(progressBarState = event.value) }
            }
            is PasswordListEvent.SearchTextChanged -> {
                updateSearchText(event.searchText)
            }
            is PasswordListEvent.FavouriteFilterChanged -> {
                updateFavouriteFilter(event.favouriteFilter)
            }
            is PasswordListEvent.UpdateSortingDialogVisibility -> {
                _state.update { it.copy(sortingDialogVisibility = event.visibility) }
            }
            is PasswordListEvent.SortingTypeChanged -> {
                updateSortingType(event.sortingType)
            }
            is PasswordListEvent.CopyPassword -> {
                copyPassword(event.passwordId)
            }
            is PasswordListEvent.ToggleFavourite -> {
                toggleFavourite(event.passwordId)
            }
        }
    }

    private fun filterAndSortPasswords() = viewModelScope.launch(Dispatchers.Default) {
        _state.update {
            val passwords = passwords.value
            val filteredPasswords = filterPasswords(passwords, it.filterViewState)
            val sortedPasswords = sortPasswords(filteredPasswords, it.sortingType)
            it.copy(passwordViewStates = sortedPasswords.toPasswordViewStates())
        }
    }

    private fun filterPasswords(passwords: List<Password>, filterViewState: FilterViewState) =
        useCases.filterPassword(
            passwords = passwords,
            passwordFilter = filterViewState.toPasswordFilter()
        )

    private fun sortPasswords(passwords: List<Password>, sortingType: PasswordSortingType) =
        useCases.sortPasswords(
            passwords = passwords,
            sortingType = sortingType
        )

    private fun List<Password>.toPasswordViewStates() = this.map { it.toPasswordViewState() }

    private fun updateSearchText(searchText: String) {
        updateFilterState(_state.value.filterViewState.copy(searchText = searchText))
    }

    private fun updateFavouriteFilter(favouriteFilter: FavouriteFilter) {
        updateFilterState(_state.value.filterViewState.copy(favouriteFilter = favouriteFilter))
    }

    private fun updateFilterState(filterViewState: FilterViewState) {
        _state.update { it.copy(filterViewState = filterViewState) }
        onTriggerEvent(PasswordListEvent.FilterAndSortPasswords)
    }

    private fun updateSortingType(sortingType: PasswordSortingType) {
        _state.update { it.copy(sortingType = sortingType) }
        onTriggerEvent(PasswordListEvent.FilterAndSortPasswords)
    }

    private fun copyPassword(passwordId: Int) = viewModelScope.launch(Dispatchers.Default) {
        useCases.copyPassword(passwordId)
    }

    private fun toggleFavourite(passwordId: Int) = viewModelScope.launch(Dispatchers.Default) {
        useCases.toggleFavourite(passwordId)
    }
}