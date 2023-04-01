package ru.dimagor555.password.ui.listscreen.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun SearchTextField(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClearSearchText: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier,
        value = searchText,
        onValueChange = onSearchTextChange,
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        placeholder = { SearchPlaceholder() },
        leadingIcon = { SearchIcon() },
        trailingIcon = { ClearTextIcon(searchText, onClearSearchText) },
        colors = searchTextFieldColors(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
    )
}

@Composable
private fun SearchPlaceholder() {
    Text(
        text = stringResource(MR.strings.search),
        style = MaterialTheme.typography.body1,
    )
}

@Composable
private fun SearchIcon() {
    Icon(
        Icons.Default.Search,
        contentDescription = stringResource(MR.strings.search),
    )
}

@Composable
private fun ClearTextIcon(text: String, onClearText: () -> Unit) {
    if (text.isEmpty())
        return
    Icon(
        Icons.Default.Clear,
        contentDescription = stringResource(MR.strings.clear),
        modifier = Modifier.clickable(onClick = onClearText),
    )
}

@Composable
private fun searchTextFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    cursorColor = MaterialTheme.colors.secondary
)

@Preview
@Composable
private fun EmptySearchTextFieldPreview() {
    PasswordManagerTheme {
        Surface {
            SearchTextField(
                modifier = Modifier.padding(10.dp),
                searchText = "",
                onSearchTextChange = {},
                onClearSearchText = {},
            )
        }
    }
}

@Preview
@Composable
private fun FilledSearchTextFieldPreview() {
    PasswordManagerTheme {
        Surface {
            SearchTextField(
                modifier = Modifier.padding(10.dp),
                searchText = "Google",
                onSearchTextChange = {},
                onClearSearchText = {},
            )
        }
    }
}