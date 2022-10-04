package ru.dimagor555.passwordgeneration.ui.screen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.passwordgeneration.ui.screen.PasswordGeneration.*
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.*

fun createPasswordGenerationComponent(
    componentContext: ComponentContext,
    onNavigateBack: (Result) -> Unit
): ComponentContext =
    PasswordGenerationComponent(componentContext, onNavigateBack)

internal class PasswordGenerationComponent constructor(
    componentContext: ComponentContext,
    private val onNavigateBack: (Result) -> Unit
) : MviComponentContext<Action, State, SideEffect>(
    componentContext = componentContext,
    storeFactory = { PasswordGenerationStore() },
), PasswordGeneration {

    fun navigateBack(result: String?) {
        onNavigateBack(Result.GeneratedPassword(result))
    }

}