package ru.dimagor555.password.editingcore.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dimagor555.password.editingcore.model.TextFieldViewState
import ru.dimagor555.password.ui.core.RowWithSmallHeadline
import ru.dimagor555.ui.core.components.SimpleErrorOutlinedTextField

@Composable
internal fun TextInputFieldWithHeadline(
    state: TextFieldViewState,
    onValueChange: (String) -> Unit,
    headline: String,
    modifier: Modifier = Modifier
) {
    RowWithSmallHeadline(headline = headline) {
        SimpleErrorOutlinedTextField(
            modifier = modifier,
            value = state.text,
            onValueChange = onValueChange,
            error = state.error,
            singleLine = true
        )
    }
}