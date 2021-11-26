package ru.dimagor555.ui.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SimpleErrorOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false
) {
    val isError = error != null
    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            trailingIcon = {
                when {
                    trailingIcon != null -> trailingIcon()
                    isError -> ErrorIcon()
                }
            },
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            singleLine = singleLine
        )
        Spacer(modifier = Modifier.height(2.dp))
        ErrorMessage(error ?: "")
    }
}

@Composable
private fun ErrorIcon() {
    Icon(imageVector = Icons.Default.Error, contentDescription = null)
}

@Composable
private fun ErrorMessage(error: String) {
    Box(
        modifier = Modifier
            .requiredHeight(16.dp)
            .padding(start = 16.dp, end = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        ProvideMediumAlpha {
            Text(
                text = error,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.error
            )
        }
    }
}