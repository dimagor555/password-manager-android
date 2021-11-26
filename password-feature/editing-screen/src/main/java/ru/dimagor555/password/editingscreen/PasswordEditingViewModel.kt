package ru.dimagor555.password.editingscreen

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.dimagor555.password.editingcore.CommonPasswordEditingUseCases
import ru.dimagor555.password.editingcore.CommonPasswordEditingViewModel
import ru.dimagor555.password.editingcore.model.PasswordEditingDto
import ru.dimagor555.password.usecase.UpdatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
internal class PasswordEditingViewModel @Inject constructor(
    private val useCases: PasswordEditingUseCases,
    commonUseCases: CommonPasswordEditingUseCases,
    savedStateHandle: SavedStateHandle
) : CommonPasswordEditingViewModel(commonUseCases) {
    private val passwordId = savedStateHandle.get<Int>("passwordId")
        ?: error("passwordId argument is not passed")

    lateinit var initialPasswordDto: PasswordEditingDto

    override suspend fun getInitialPasswordDto(): PasswordEditingDto {
        createInitialPasswordDto()
        return initialPasswordDto
    }

    private suspend fun createInitialPasswordDto() {
        val password = useCases.getPassword(passwordId)
        val decryptedPassword = useCases.decryptPassword(passwordId)
        initialPasswordDto = PasswordEditingDto(password.title, password.login, decryptedPassword)
    }

    override suspend fun onFinishEditing(passwordDto: PasswordEditingDto) = with(passwordDto) {
        if (passwordDto == initialPasswordDto)
            return@with
        useCases.updatePassword(
            UpdatePasswordUseCase.Params(passwordId, title, login, password)
        )
    }
}
