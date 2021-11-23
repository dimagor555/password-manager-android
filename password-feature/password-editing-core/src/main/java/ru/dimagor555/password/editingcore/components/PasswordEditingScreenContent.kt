package ru.dimagor555.password.editingcore.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.domain.validation.ValidationError
import ru.dimagor555.password.editingcore.R
import ru.dimagor555.password.editingcore.model.PasswordEditingViewState
import ru.dimagor555.password.editingcore.model.TextFieldViewState
import ru.dimagor555.password.editingcore.model.toStringMessage
import ru.dimagor555.password.ui.core.LargePaddingColumn
import ru.dimagor555.password.ui.core.RowWithSmallHeadline
import ru.dimagor555.password.validation.PasswordValidationError
import ru.dimagor555.ui.core.components.SimpleButton
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
fun PasswordEditingScreenContent(
    state: PasswordEditingViewState,
    onTitleChange: (String) -> Unit,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onGenerateClick: () -> Unit
) {
    LargePaddingColumn(modifier = Modifier.fillMaxWidth()) {
        val topPaddingModifier = Modifier.padding(top = 4.dp)
        TitleInputField(
            state = state.titleState,
            onTitleChange = onTitleChange,
            modifier = topPaddingModifier
        )
        LoginInputField(
            state = state.loginState,
            onLoginChange = onLoginChange,
            modifier = topPaddingModifier
        )
        PasswordInputField(
            state = state.passwordState,
            onPasswordChange = onPasswordChange,
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            modifier = topPaddingModifier
        )
        SimpleButton(
            text = stringResource(R.string.generate),
            onClick = onGenerateClick
        )
    }
}

@Composable
private fun TitleInputField(
    state: TextFieldViewState,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextInputFieldWithHeadline(
        state = state,
        onValueChange = onTitleChange,
        headline = stringResource(R.string.title),
        modifier = modifier
    )
}

@Composable
private fun LoginInputField(
    state: TextFieldViewState,
    onLoginChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextInputFieldWithHeadline(
        state = state,
        onValueChange = onLoginChange,
        headline = stringResource(R.string.login),
        modifier = modifier
    )
}

@Composable
private fun PasswordInputField(
    state: TextFieldViewState,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    RowWithSmallHeadline(headline = stringResource(R.string.password)) {
        ru.dimagor555.ui.core.components.PasswordInputField(
            value = state.text,
            onValueChange = onPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            error = state.error,
            modifier = modifier
        )
    }
}

@Preview("Password editing screen content")
@Preview("Password editing screen content (ru)", locale = "ru")
@Preview("Password editing screen content (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PasswordEditingScreenContentPreview() {
    PasswordManagerTheme {
        Surface {
            PasswordEditingScreenContent(
                state = PasswordEditingViewState(
                    titleState = TextFieldViewState(text = "Google"),
                    loginState = TextFieldViewState(
                        text = "",
                        error = ValidationError.IsBlank.toStringMessage(LocalContext.current)
                    ),
                    passwordState = TextFieldViewState(
                        text = "test password",
                        error = PasswordValidationError.IsTooShort(16)
                            .toStringMessage(LocalContext.current)
                    )
                ),
                {}, {}, {}, {}, {}
            )
        }
    }
}