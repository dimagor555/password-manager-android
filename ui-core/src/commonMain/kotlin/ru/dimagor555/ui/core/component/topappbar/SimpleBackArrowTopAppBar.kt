package ru.dimagor555.ui.core.component.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dimagor555.ui.core.component.button.ArrowBackIconButton

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