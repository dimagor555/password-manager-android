package ru.dimagor555.ui.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultTopAppBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 12.dp)
    ) {
        content()
    }
}