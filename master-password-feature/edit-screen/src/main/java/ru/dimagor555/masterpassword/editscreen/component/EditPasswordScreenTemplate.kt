package ru.dimagor555.masterpassword.editscreen.component

import android.content.res.Configuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.masterpassword.editscreen.R
import ru.dimagor555.masterpassword.ui.core.PasswordErrorIndicator
import ru.dimagor555.ui.core.component.textfield.SimpleErrorOutlinedTextField
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import kotlin.random.Random

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

@Preview("EditPasswordScreenTemplate")
@Preview("EditPasswordScreenTemplate (ru)", locale = "ru")
@Preview("EditPasswordScreenTemplate (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditPasswordScreenTemplatePreview() {
    PasswordManagerTheme {
        Surface() {
            EditPasswordScreenTemplate(
                title = stringResource(R.string.primary_password_screen_title),
                isError = Random.nextBoolean(),
                bottomButton = {
                    Button(onClick = {}) {
                        Text(text = stringResource(R.string.next))
                    }
                }
            ) {
                SimpleErrorOutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            text = stringResource(R.string.confirm_password_field_placeholder),
                            style = MaterialTheme.typography.body1
                        )
                    },
                    error = stringResource(R.string.error_password_not_match)
                )
            }
        }
    }
}
