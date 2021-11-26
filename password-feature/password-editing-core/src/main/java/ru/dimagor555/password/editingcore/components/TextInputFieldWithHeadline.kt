package ru.dimagor555.password.editingcore.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import ru.dimagor555.password.editingcore.model.FieldViewState
import ru.dimagor555.password.ui.core.RowWithSmallHeadline
import ru.dimagor555.ui.core.components.SimpleErrorOutlinedTextField

@Composable
internal fun TextInputFieldWithHeadline(
    state: FieldViewState,
    onValueChange: (String) -> Unit,
    headline: String,
    modifier: Modifier = Modifier
) {
    RowWithSmallHeadline(headline = headline) {
        SimpleErrorOutlinedTextField(
            modifier = modifier,
            value = state.text,
            onValueChange = onValueChange,
            error = state.error?.getMessage(LocalContext.current),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true
        )
    }
}