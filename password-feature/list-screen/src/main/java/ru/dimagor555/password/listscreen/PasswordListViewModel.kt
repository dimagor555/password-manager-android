package ru.dimagor555.password.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.dimagor555.core.DataState
import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.domain.filter.PasswordSortingType
import ru.dimagor555.password.listscreen.model.PasswordListEvent
import ru.dimagor555.password.listscreen.model.PasswordListViewState
import ru.dimagor555.password.listscreen.model.toPasswordViewStates
import javax.inject.Inject

@HiltViewModel
internal class PasswordListViewModel @Inject constructor(
    private val useCases: PasswordListUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(PasswordListViewState())
    val state = _state.asStateFlow()

    init {
        observePasswords()
        observePasswordFilter()
    }

    private fun observePasswords() {
        useCases.observePasswords()
            .onEach { unwrapPasswordsDataState(it) }
            .launchIn(viewModelScope)
    }

    private suspend fun unwrapPasswordsDataState(dataState: DataState<List<Password>>) {
        when (dataState) {
            is DataState.Data -> {
                updatePasswords(dataState.data)
                sendEvent(PasswordListEvent.UpdateProgressBarState(ProgressBarState.Idle))
            }
            is DataState.Loading -> {
                sendEvent(PasswordListEvent.UpdateProgressBarState(ProgressBarState.Loading))
            }
            else -> error("Illegal data state")
        }
    }

    private suspend fun updatePasswords(passwords: List<Password>) {
        val passwordViewStates = passwords.toPasswordViewStates()
        _state.update { it.copy(passwordViewStates = passwordViewStates) }
    }

    private fun observePasswordFilter() {
        useCases.observePasswordFilterState()
            .onEach { updateFilterState(it) }
            .launchIn(viewModelScope)
    }

    private fun updateFilterState(filterState: PasswordFilterState) {
        _state.update { it.copy(filterState = filterState) }
    }

    fun sendEvent(event: PasswordListEvent) {
        when (event) {
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

    private fun updateSearchText(searchText: String) = viewModelScope.launch {
        useCases.updatePasswordFilterState(
            _state.value.filterState.copy(searchText = searchText)
        )
    }

    private fun updateFavouriteFilter(favouriteFilter: FavouriteFilter) = viewModelScope.launch {
        useCases.updatePasswordFilterState(
            _state.value.filterState.copy(favouriteFilter = favouriteFilter)
        )
    }

    private fun updateSortingType(sortingType: PasswordSortingType) = viewModelScope.launch {
        useCases.updatePasswordFilterState(
            _state.value.filterState.copy(sortingType = sortingType)
        )
    }

    private fun copyPassword(passwordId: Int) = viewModelScope.launch {
        useCases.copyPassword(passwordId)
    }

    private fun toggleFavourite(passwordId: Int) = viewModelScope.launch {
        useCases.toggleFavourite(passwordId)
    }
}