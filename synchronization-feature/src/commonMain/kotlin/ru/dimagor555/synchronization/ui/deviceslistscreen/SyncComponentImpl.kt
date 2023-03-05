package ru.dimagor555.synchronization.ui.deviceslistscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.synchronization.ui.deviceslistscreen.SyncComponent.SyncComponentCallbacks
import ru.dimagor555.synchronization.ui.deviceslistscreen.model.SyncStore
import ru.dimagor555.synchronization.ui.deviceslistscreen.model.SyncStore.*

fun createSyncComponent(
    componentContext: ComponentContext,
    callbacks: SyncComponentCallbacks,
): SyncComponent = SyncComponentImpl(componentContext, callbacks,)
internal class SyncComponentImpl constructor(
    componentContext: ComponentContext,
    val callbacks: SyncComponentCallbacks,
) : MviComponentContext<Action, State, SideEffect>(
    componentContext = componentContext,
    storeFactory = { SyncStore() },
), SyncComponent