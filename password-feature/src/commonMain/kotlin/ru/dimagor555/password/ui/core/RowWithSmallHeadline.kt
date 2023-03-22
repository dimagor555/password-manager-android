package ru.dimagor555.password.ui.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dimagor555.ui.core.util.ProvideMediumAlpha

@Composable
fun RowWithSmallHeadline(
    headline: String,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Column(modifier = modifier) {
        ProvideMediumAlpha {
            Text(
                text = headline,
                style = MaterialTheme.typography.overline
            )
        }
        Row(content = content)
    }
}