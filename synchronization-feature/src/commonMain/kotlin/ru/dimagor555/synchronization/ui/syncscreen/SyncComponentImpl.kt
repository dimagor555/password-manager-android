package ru.dimagor555.synchronization.ui.syncscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore.Action
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore.State

fun createSyncComponent(
    componentContext: ComponentContext,
    callbacks: SyncComponent.SyncComponentCallbacks,
    isClient: Boolean,
): SyncComponent = SyncComponentImpl(componentContext, callbacks, isClient)

internal class SyncComponentImpl constructor(
    componentContext: ComponentContext,
    val callbacks: SyncComponent.SyncComponentCallbacks,
    private val isClient: Boolean,
) : MviComponentContext<Action,State, Nothing>(
    componentContext = componentContext,
    storeFactory = { SyncStore(isClient) },
), SyncComponent
