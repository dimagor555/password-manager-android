package ru.dimagor555.password.listscreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.listscreen.R
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun SearchTextField(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = searchText,
        onValueChange = onSearchTextChange,
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        placeholder = { SearchPlaceholder() },
        leadingIcon = { SearchIcon() },
        trailingIcon = { ClearTextIcon(searchText, onSearchTextChange) },
        colors = searchTextFieldColors()
    )
}

@Composable
private fun SearchPlaceholder() {
    Text(
        text = stringResource(R.string.search),
        style = MaterialTheme.typography.body1
    )
}

@Composable
private fun SearchIcon() {
    Icon(
        Icons.Default.Search,
        contentDescription = stringResource(R.string.search)
    )
}

@Composable
private fun ClearTextIcon(text: String, onTextChange: (String) -> Unit) {
    if (text.isEmpty())
        return
    Icon(
        Icons.Default.Clear,
        contentDescription = stringResource(R.string.clear),
        modifier = Modifier.clickable { onTextChange("") }
    )
}

@Composable
private fun searchTextFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    cursorColor = MaterialTheme.colors.secondary
)

@Preview("Empty search text field")
@Preview("Empty search text field (ru)", locale = "ru")
@Composable
private fun EmptySearchTextFieldPreview() {
    PasswordManagerTheme {
        Surface {
            SearchTextField(
                modifier = Modifier.padding(10.dp),
                searchText = "",
                onSearchTextChange = {})
        }
    }
}

@Preview("Filled search text field")
@Composable
private fun FilledSearchTextFieldPreview() {
    PasswordManagerTheme {
        Surface {
            SearchTextField(
                modifier = Modifier.padding(10.dp),
                searchText = "Google",
                onSearchTextChange = {})
        }
    }
}