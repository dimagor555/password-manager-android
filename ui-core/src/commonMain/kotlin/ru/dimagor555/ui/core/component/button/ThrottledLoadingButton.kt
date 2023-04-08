package ru.dimagor555.ui.core.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.ui.core.util.rememberThrottledFalseBoolean
import ru.dimagor555.ui.core.util.rememberThrottledTrueBoolean

@Composable
fun ThrottledLoadingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    val isEnabled = rememberThrottledFalseBoolean(enabled)
    val isLoading = rememberThrottledTrueBoolean(loading)
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = contentPadding,
        enabled = isEnabled,
    ) {
        val loaderModifier = Modifier
            .padding(start = 4.dp)
            .requiredSize(16.dp)
        if (isLoading) {
            Spacer(modifier = loaderModifier)
        }
        Text(text = text)
        if (isLoading) {
            ButtonLoader(modifier = loaderModifier)
        }
    }
}

@Composable
private fun ButtonLoader(
    modifier: Modifier = Modifier,
) {
    val color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = 2.dp,
    )
}