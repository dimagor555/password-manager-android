package ru.dimagor555.synchronization.ui.resultsyncscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.synchronization.ui.resultsyncscreen.ResultSyncComponent.ResultSyncComponentCallbacks
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncStore
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncStore.Action
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncStore.State

fun createResultSyncComponent(
    componentContext: ComponentContext,
    callbacks: ResultSyncComponentCallbacks,
    isSyncSuccess: Boolean,
): ResultSyncComponent = ResultSyncComponentImpl(componentContext, callbacks, isSyncSuccess)

internal class ResultSyncComponentImpl constructor(
    componentContext: ComponentContext,
    val callbacks: ResultSyncComponentCallbacks,
    private val isSyncSuccess: Boolean,
) : MviComponentContext<Action,State, Nothing>(
    componentContext = componentContext,
    storeFactory = { ResultSyncStore(isSyncSuccess) },
), ResultSyncComponent
