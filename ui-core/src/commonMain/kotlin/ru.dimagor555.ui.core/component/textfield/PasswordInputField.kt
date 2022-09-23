package ru.dimagor555.ui.core.component.textfield

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.dimagor555.ui.core.component.button.VisibilityIconButton

@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholder: (@Composable () -> Unit)? = null,
    error: String? = null,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true
) {
    SimpleErrorOutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        error = error,
        placeholder = placeholder,
        trailingIcon = {
            VisibilityIconButton(
                isVisible = isPasswordVisible,
                onToggleVisibility = onTogglePasswordVisibility
            )
        },
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        visualTransformation = chooseVisualTransformation(isPasswordVisible),
        singleLine = singleLine
    )
}

private fun chooseVisualTransformation(isPasswordVisible: Boolean) =
    if (isPasswordVisible)
        VisualTransformation.None
    else
        PasswordVisualTransformation()