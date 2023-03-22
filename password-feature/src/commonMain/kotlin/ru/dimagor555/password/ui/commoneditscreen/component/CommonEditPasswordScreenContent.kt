package ru.dimagor555.password.ui.commoneditscreen.component

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.domain.password.field.*
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore.State
import ru.dimagor555.password.ui.core.LargePaddingColumn
import ru.dimagor555.password.ui.core.RowWithSmallHeadline
import ru.dimagor555.res.core.MR
import ru.dimagor555.password.validation.core.TextValidationError
import ru.dimagor555.password.validation.ui.desc
import ru.dimagor555.ui.core.component.button.SimpleButton
import ru.dimagor555.ui.core.model.FieldState
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.bringIntoViewOnFocus
import ru.dimagor555.ui.core.util.resolve
import ru.dimagor555.ui.core.util.stringResource

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
internal fun CommonEditPasswordScreenContent(
    state: State,
    onTitleChange: (String) -> Unit,
    onSiteChange: (String) -> Unit,
    onLoginChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onValidate: () -> Unit,
    onGenerateButtonClick: () -> Unit,
) {
    LargePaddingColumn(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalSpacing = 8.dp,
    ) {
        val inputFieldModifier = Modifier
            .padding(top = 4.dp)
            .bringIntoViewOnFocus()
        state.fieldsByKeys.map {
            when (it.key) {
                TITLE_FIELD_KEY -> TextInputFieldWithHeadline(
                    state = state.fieldsByKeys[TITLE_FIELD_KEY]!!,
                    onValueChange = onTitleChange,
                    modifier = inputFieldModifier,
                    headline = stringResource(MR.strings.title)
                )
                LOGIN_FIELD_KEY -> TextInputFieldWithHeadline(
                    state = state.fieldsByKeys[LOGIN_FIELD_KEY]!!,
                    onValueChange = onLoginChange,
                    modifier = inputFieldModifier,
                    headline = stringResource(MR.strings.login)
                )
                PHONE_FIELD_KEY -> TextInputFieldWithHeadline(
                    state = state.fieldsByKeys[PHONE_FIELD_KEY]!!,
                    onValueChange = onPhoneChange,
                    modifier = inputFieldModifier,
                    headline = stringResource(MR.strings.phone)
                )
                PASSWORD_FIELD_KEY -> PasswordInputField(
                    state = state.fieldsByKeys[PASSWORD_FIELD_KEY]!! as FieldState.Password,
                    onPasswordChange = onPasswordChange,
                    onTogglePasswordVisibility = onTogglePasswordVisibility,
                    onValidate = onValidate,
                    modifier = inputFieldModifier,
                )
                SITE_FIELD_KEY -> TextInputFieldWithHeadline(
                    state = state.fieldsByKeys[SITE_FIELD_KEY]!!,
                    onValueChange = onSiteChange,
                    modifier = inputFieldModifier,
                    headline = stringResource(MR.strings.site)
                )
            }
        }
        SimpleButton(
            text = stringResource(MR.strings.generate),
            onClick = onGenerateButtonClick,
        )
    }
}

@ExperimentalComposeUiApi
@Composable
private fun PasswordInputField(
    state: FieldState.Password,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onValidate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    RowWithSmallHeadline(headline = stringResource(MR.strings.password)) {
        ru.dimagor555.ui.core.component.textfield.PasswordInputField(
            value = state.text,
            onValueChange = onPasswordChange,
            isPasswordVisible = state.isVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            error = state.error?.resolve(),
            modifier = modifier,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                onValidate()
            }),
        )
    }
}

@Preview
@Composable
private fun PasswordEditingScreenContentPreview() {
    PasswordManagerTheme {
        Surface {
            CommonEditPasswordScreenContent(
                state = State(
                    fieldsByKeys = mapOf(
                        TITLE_FIELD_KEY to FieldState.Text(text = "Google"),
                        LOGIN_FIELD_KEY to FieldState.Text(
                            text = "",
                            error = TextValidationError.IsBlank.desc()
                        ),
                        PASSWORD_FIELD_KEY to FieldState.Password(
                            text = "test password",
                            error = TextValidationError.IsTooShort(16).desc()
                        )
                    )
                ),
                {}, {}, {}, {}, {}, {}, {}, {},
            )
        }
    }
}
