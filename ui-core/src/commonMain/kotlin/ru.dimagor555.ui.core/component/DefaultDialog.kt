package ru.dimagor555.ui.core.component

import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun DefaultDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onCloseRequest = onDismissRequest) {
        Card {
            content()
        }
    }
}