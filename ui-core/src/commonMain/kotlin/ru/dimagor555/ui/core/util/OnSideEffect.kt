package ru.dimagor555.ui.core.util

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.res.core.MR

@Composable
fun <SideEffect : Any> OnSideEffect(
    component: MviComponentContext<*, *, SideEffect>,
    onSideEffect: (SideEffect) -> Unit,
) {
    LaunchedEffect(Unit) {
        component.sideEffects.collect { onSideEffect(it) }
    }
}

@Composable
fun <SideEffect : Any> OnSideEffect(
    component: MviComponentContext<*, *, SideEffect>,
    snackbarHostState: SnackbarHostState,
    onSideEffect: (SideEffect, showSnackbar: ShowSnackbar) -> Unit,
) {
    var snackbarDesc: SnackbarDesc? by remember { mutableStateOf(null) }
    val showSnackbar: ShowSnackbar = remember { { snackbarDesc = it } }

    LaunchedEffect(Unit) {
        component.sideEffects.collect { onSideEffect(it, showSnackbar) }
    }

    val snackbar by rememberUpdatedState(snackbarDesc?.resolve())
    LaunchedEffect(snackbar) {
        snackbar?.let {
            snackbarHostState.showSingleSnackbar(it)
            snackbarDesc = null
        }
    }
}

//TODO костыль, следует либо переделать все SideEffect под Store, вместо MviComponentContext, либо
//придумать что-то ещё
@Composable
fun <SideEffect : Any> OnStoreSideEffect(
    store: Store<*, *, SideEffect>,
    snackbarHostState: SnackbarHostState,
    onSideEffect: (SideEffect, showSnackbar: ShowSnackbar) -> Unit,
) {
    var snackbarDesc: SnackbarDesc? by remember { mutableStateOf(null) }
    val showSnackbar: ShowSnackbar = remember { { snackbarDesc = it } }

    LaunchedEffect(Unit) {
        store.sideEffects.collect { onSideEffect(it, showSnackbar) }
    }

    val snackbar by rememberUpdatedState(snackbarDesc?.resolve())
    LaunchedEffect(snackbar) {
        snackbar?.let {
            snackbarHostState.showSingleSnackbar(it)
            snackbarDesc = null
        }
    }
}

private typealias ShowSnackbar = (SnackbarDesc) -> Unit

fun createLongSnackbarMessage(message: StringDesc) =
    SnackbarDesc(
        message = message,
        actionLabel = MR.strings.ok.desc(),
        duration = SnackbarDuration.Long
    )
