package ru.dimagor555.password.editingcore.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.editingcore.R
import ru.dimagor555.password.editingcore.model.FieldViewState
import ru.dimagor555.password.editingcore.model.PasswordEditingViewState
import ru.dimagor555.password.editingcore.model.TextFieldError
import ru.dimagor555.password.ui.core.LargePaddingColumn
import ru.dimagor555.password.ui.core.RowWithSmallHeadline
import ru.dimagor555.password.validation.TextValidationError
import ru.dimagor555.ui.core.components.SimpleButton
import ru.dimagor555.ui.core.components.bringIntoViewOnFocus
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
internal fun PasswordEditingScreenContent(
    state: PasswordEditingViewState,
    onTitleChange: (String) -> Unit,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onTryFinishEditing: () -> Unit,
    onGenerateClick: () -> Unit
) {
    LargePaddingColumn(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalSpacing = 8.dp
    ) {
        val inputFieldModifier = Modifier
            .padding(top = 4.dp)
            .bringIntoViewOnFocus()
        TitleInputField(
            state = state.titleState,
            onTitleChange = onTitleChange,
            modifier = inputFieldModifier
        )
        LoginInputField(
            state = state.loginState,
            onLoginChange = onLoginChange,
            modifier = inputFieldModifier
        )
        PasswordInputField(
            state = state.passwordState,
            onPasswordChange = onPasswordChange,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            onTryFinishEditing = onTryFinishEditing,
            modifier = inputFieldModifier
        )
        SimpleButton(
            text = stringResource(R.string.generate),
            onClick = onGenerateClick
        )
    }
}

@Composable
private fun TitleInputField(
    state: FieldViewState,
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
    state: FieldViewState,
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

@ExperimentalComposeUiApi
@Composable
private fun PasswordInputField(
    state: FieldViewState.Password,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onTryFinishEditing: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    RowWithSmallHeadline(headline = stringResource(R.string.password)) {
        ru.dimagor555.ui.core.components.PasswordInputField(
            value = state.text,
            onValueChange = onPasswordChange,
            isPasswordVisible = state.isVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            error = state.error?.getMessage(LocalContext.current),
            modifier = modifier,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onTryFinishEditing()
            })
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
                    titleState = FieldViewState.Text(text = "Google"),
                    loginState = FieldViewState.Text(
                        text = "",
                        error = TextFieldError(TextValidationError.IsBlank)
                    ),
                    passwordState = FieldViewState.Password(
                        text = "test password",
                        error = TextFieldError(TextValidationError.IsTooShort(16))
                    )
                ),
                {}, {}, {}, {}, {}, {}
            )
        }
    }
}