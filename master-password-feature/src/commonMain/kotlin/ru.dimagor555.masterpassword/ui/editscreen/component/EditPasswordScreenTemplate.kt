package ru.dimagor555.masterpassword.ui.editscreen.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.masterpassword.ui.core.PasswordErrorIndicator
import ru.dimagor555.ui.core.component.textfield.SimpleErrorOutlinedTextField
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import kotlin.random.Random
import ru.dimagor555.ui.core.UiCoreRes
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun EditPasswordScreenTemplate(
    title: String,
    isError: Boolean,
    bottomButton: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    EditPasswordColumn {
        EditPasswordTitle(title = title)
        PasswordErrorIndicator(isError = isError)
        content()
        Spacer(modifier = Modifier.weight(1f))
        bottomButton()
    }
}

@Composable
private fun EditPasswordColumn(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        content = content
    )
}

@Preview
@Composable
private fun EditPasswordScreenTemplatePreview() {
    PasswordManagerTheme {
        Surface() {
            EditPasswordScreenTemplate(
                title = stringResource(MR.strings.primary_password_screen_title),
                isError = Random.nextBoolean(),
                bottomButton = {
                    Button(onClick = {}) {
                        Text(text = stringResource(MR.strings.next))
                    }
                }
            ) {
                SimpleErrorOutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            text = stringResource(MR.strings.confirm_password_field_placeholder),
                            style = MaterialTheme.typography.body1
                        )
                    },
                    error = stringResource(MR.strings.error_password_not_match)
                )
            }
        }
    }
}
