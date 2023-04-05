package ru.dimagor555.export.ui.exportscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.export.ui.exportscreen.store.ExportStore
import ru.dimagor555.export.ui.exportscreen.store.ExportStore.*

internal class ExportComponentImpl(
    componentContext: ComponentContext,
    val onNavigateBack: () -> Unit,
) : MviComponentContext<Action, State, SideEffect>(
    componentContext = componentContext,
    storeFactory = { ExportStore() },
), ExportComponent