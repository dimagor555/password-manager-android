package ru.dimagor555.ui.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.ui.core.util.desktop
import ru.dimagor555.ui.core.util.onMobile

@Composable
fun MultiplatformTopBottomLayout(
    topContent: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .onMobile { fillMaxHeight() }
            .verticalScroll(rememberScrollState())
            .padding(all = 16.dp)
            .onMobile { padding(top = 16.dp) },
        verticalArrangement = Arrangement.SpaceBetween desktop Arrangement.spacedBy(16.dp),
    ) {
        topContent()
        bottomContent()
    }
}