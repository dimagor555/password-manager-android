package ru.dimagor555.passwordgeneration.ui.screen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.passwordgeneration.ui.screen.PasswordGenerationComponent.*
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.*

fun createPasswordGenerationComponent(
    componentContext: ComponentContext,
    onNavigateBack: (Result.GeneratedPassword) -> Unit
): PasswordGenerationComponent =
    PasswordGenerationComponentImpl(componentContext, onNavigateBack)

internal class PasswordGenerationComponentImpl constructor(
    componentContext: ComponentContext,
    private val onNavigateBack: (Result.GeneratedPassword) -> Unit
) : MviComponentContext<Action, State, SideEffect>(
    componentContext = componentContext,
    storeFactory = { PasswordGenerationStore() },
), PasswordGenerationComponent {

    fun navigateBack(result: String?) {
        onNavigateBack(Result.GeneratedPassword(result))
    }

}