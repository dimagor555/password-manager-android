package ru.dimagor555.password.creationscreen

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.dimagor555.password.editingcore.CommonPasswordEditingUseCases
import ru.dimagor555.password.editingcore.CommonPasswordEditingViewModel
import ru.dimagor555.password.editingcore.model.PasswordEditingDto
import ru.dimagor555.password.usecase.CreatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
internal class PasswordCreationViewModel @Inject constructor(
    private val useCases: PasswordCreationUseCases,
    commonUseCases: CommonPasswordEditingUseCases
) : CommonPasswordEditingViewModel(commonUseCases) {
    override suspend fun onFinishEditing(passwordDto: PasswordEditingDto) = with(passwordDto) {
        useCases.createPassword(CreatePasswordUseCase.Params(title, login, password))
        sendExitScreenEvent()
    }
}