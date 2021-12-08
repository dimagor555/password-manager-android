package ru.dimagor555.ui.core.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
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
                    else -> AnimatedErrorIcon(isVisible = isError)
                }
            },
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            singleLine = singleLine
        )
        Spacer(modifier = Modifier.height(2.dp))
        ErrorMessageBox {
            AnimatedErrorText(error)
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun AnimatedErrorIcon(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = null,
            tint = MaterialTheme.colors.error
        )
    }
}

@Composable
private fun ErrorMessageBox(errorMessage: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .requiredHeight(16.dp)
            .padding(start = 16.dp, end = 12.dp),
        contentAlignment = Alignment.BottomCenter,
        content = errorMessage
    )
}

@Composable
private fun AnimatedErrorText(text: String?) {
    val isVisible = text != null
    val errorText = rememberLastNotNullText(text)
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(),
        exit = slideOutVertically() + fadeOut()
    ) {
        ErrorText(errorText)
    }
}

@Composable
private fun rememberLastNotNullText(text: String?): String {
    var rememberedText by remember { mutableStateOf("") }
    if (text != null)
        rememberedText = text
    return rememberedText
}

@Composable
private fun ErrorText(text: String) {
    ProvideMediumAlpha {
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.error
        )
    }
}