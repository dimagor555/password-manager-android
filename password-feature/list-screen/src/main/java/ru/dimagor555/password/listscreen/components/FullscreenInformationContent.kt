package ru.dimagor555.password.listscreen.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.ui.core.util.ProvideMediumAlpha
import ru.dimagor555.ui.core.icon.Lock
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun FullscreenInformationContent(
    title: String,
    contentText: String,
    iconTint: Color = MaterialTheme.colors.primary
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .padding(bottom = 56.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            tint = iconTint
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1
        )
        ProvideMediumAlpha {
            Text(
                text = contentText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview("Screen with lock icon")
@Preview("Screen with lock icon (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ScreenWithLockIconPreview() {
    PasswordManagerTheme {
        Surface {
            FullscreenInformationContent(
                title = "Title",
                contentText = "Content text"
            )
        }
    }
}