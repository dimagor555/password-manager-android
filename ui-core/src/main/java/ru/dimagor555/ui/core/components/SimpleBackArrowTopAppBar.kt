package ru.dimagor555.ui.core.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleBackArrowTopAppBar(
    title: @Composable () -> Unit,
    onArrowBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = { ArrowBackIconButton(onClick = onArrowBackClick) },
        actions = actions
    )
}