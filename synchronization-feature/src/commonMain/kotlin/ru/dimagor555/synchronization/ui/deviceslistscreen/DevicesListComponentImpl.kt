package ru.dimagor555.synchronization.ui.deviceslistscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.synchronization.ui.deviceslistscreen.DevicesListComponent.DevicesListComponentCallbacks
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.Action
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.State

fun createDevicesListComponent(
    componentContext: ComponentContext,
    callbacks: DevicesListComponentCallbacks,
): DevicesListComponent = DevicesListComponentImpl(componentContext, callbacks)

internal class DevicesListComponentImpl constructor(
    componentContext: ComponentContext,
    val callbacks: DevicesListComponentCallbacks,
) : MviComponentContext<Action, State, Nothing>(
    componentContext = componentContext,
    storeFactory = { DevicesListStore() },
), DevicesListComponent