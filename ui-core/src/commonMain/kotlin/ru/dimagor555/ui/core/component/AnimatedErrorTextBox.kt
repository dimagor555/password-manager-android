package ru.dimagor555.ui.core.component

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.ui.core.util.rememberLastNotNullText

@Composable
fun AnimatedErrorTextBox(
    text: String?,
    isVisible: Boolean,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 24.dp),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn() + slideInVertically(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Text(
                text = rememberLastNotNullText(text),
                color = MaterialTheme.colors.error,
            )
        }
    }
}