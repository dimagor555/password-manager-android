package ru.dimagor555.masterpassword.ui.startscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.dimagor555.masterpassword.domain.MasterPasswordRepository

internal class StartViewModel(
    private val masterPasswordRepository: MasterPasswordRepository
) : ViewModel() {
    private val _hasMasterPassword = MutableStateFlow<Boolean?>(null)
    val hasMasterPassword = _hasMasterPassword.asStateFlow()

    init {
        viewModelScope.launch {
            _hasMasterPassword.value = masterPasswordRepository.hasPassword()
        }
    }
}

@Composable
fun observeHasMasterPassword(): State<Boolean?> {
    val viewModel = koinViewModel<StartViewModel>()
    return viewModel.hasMasterPassword.collectAsState()
}