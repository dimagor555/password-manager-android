package ru.dimagor555.password.ui.commoneditscreen.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import ru.dimagor555.password.ui.core.RowWithSmallHeadline
import ru.dimagor555.ui.core.component.textfield.SimpleErrorOutlinedTextField
import ru.dimagor555.ui.core.model.FieldState
import ru.dimagor555.ui.core.util.resolve

@Composable
internal fun TextInputFieldWithHeadline(
    state: FieldState,
    onValueChange: (String) -> Unit,
    headline: String,
    modifier: Modifier = Modifier
) {
    RowWithSmallHeadline(headline = headline) {
        SimpleErrorOutlinedTextField(
            modifier = modifier,
            value = state.text,
            onValueChange = onValueChange,
            error = state.error?.resolve(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true
        )
    }
}