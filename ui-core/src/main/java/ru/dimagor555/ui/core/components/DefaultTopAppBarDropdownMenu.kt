package ru.dimagor555.ui.core.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import ru.dimagor555.ui.core.R

@Composable
fun DefaultTopAppBarDropdownMenu(
    content: @Composable ColumnScope.(onDismiss: () -> Unit) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val onDismiss = { isExpanded = false }
    SimpleIconButton(
        icon = Icons.Default.MoreVert,
        contentDescription = stringResource(R.string.menu),
        onClick = { isExpanded = true }
    )
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss,
        content = { content(onDismiss) }
    )
}
