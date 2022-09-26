package ru.dimagor555.ui.core.component.topappbar

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.button.SimpleIconButton
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun DefaultTopAppBarDropdownMenu(
    content: @Composable ColumnScope.(onDismiss: () -> Unit) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val onDismiss = { isExpanded = false }
    SimpleIconButton(
        icon = Icons.Default.MoreVert,
        contentDescription = stringResource(MR.strings.menu),
        onClick = { isExpanded = true }
    )
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss,
        content = { content(onDismiss) }
    )
}
