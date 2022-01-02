package ru.dimagor555.password.detailsscreen.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.ui.core.RowWithSmallHeadline
import ru.dimagor555.ui.core.component.button.SimpleIconButton
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun CopyableTextRow(
    text: String,
    headline: String,
    buttonContentDescription: String,
    onCopy: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RowWithSmallHeadline(
            modifier = Modifier.weight(1f),
            headline = headline
        ) {
            Text(text = text, style = MaterialTheme.typography.subtitle2)
        }
        SimpleIconButton(
            icon = Icons.Default.ContentCopy,
            contentDescription = buttonContentDescription,
            onClick = onCopy
        )
    }
}

@Preview("Copyable text row")
@Preview("Copyable text row (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun CopyableTextRowPreview() {
    PasswordManagerTheme {
        Surface {
            CopyableTextRow(
                text = "Username1234",
                headline = "Login",
                buttonContentDescription = "Copy login",
                onCopy = { }
            )
        }
    }
}