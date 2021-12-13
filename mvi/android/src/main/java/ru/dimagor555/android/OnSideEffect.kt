package ru.dimagor555.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.collect

@Composable
fun <SideEffect : Any> OnSideEffect(
    mviViewModel: MviViewModel<*, *, SideEffect>,
    onSideEffect: (SideEffect) -> Unit
) {
    LaunchedEffect(Unit) {
        mviViewModel.sideEffects.collect { onSideEffect(it) }
    }
}