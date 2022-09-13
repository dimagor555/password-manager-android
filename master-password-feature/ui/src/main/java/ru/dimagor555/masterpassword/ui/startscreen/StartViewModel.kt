package ru.dimagor555.masterpassword.ui.startscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import javax.inject.Inject

@HiltViewModel
internal class StartViewModel @Inject constructor(
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
    val viewModel: StartViewModel = hiltViewModel()
    return viewModel.hasMasterPassword.collectAsState()
}