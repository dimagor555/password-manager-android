package ru.dimagor555.export.ui.importscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.export.ui.importscreen.store.ImportStore
import ru.dimagor555.export.ui.importscreen.store.ImportStore.*

internal class ImportComponentImpl(
    componentContext: ComponentContext,
    val onNavigateBack: () -> Unit,
) : MviComponentContext<Action, State, SideEffect>(
    componentContext = componentContext,
    storeFactory = { ImportStore() },
), ImportComponent