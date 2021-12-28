package ru.dimagor555.masterpassword.editscreen.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.masterpassword.editscreen.R
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun EditPasswordTitle(title: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TitleText(text = title)
        MasterPasswordWarning()
        MasterPasswordAdvice()
    }
}

@Composable
private fun TitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun MasterPasswordWarning() {
    GenericTextInRectangle(
        text = stringResource(R.string.edit_password_screen_warning),
        icon = Icons.Default.Warning,
        iconTint = WarningIconColor
    )
}

@Composable
private fun MasterPasswordAdvice() {
    GenericTextInRectangle(
        text = stringResource(R.string.edit_password_screen_advice),
        icon = Icons.Default.Info,
        iconTint = AdviceIconColor
    )
}

@Composable
private fun GenericTextInRectangle(
    text: String,
    icon: ImageVector,
    iconTint: Color
) {
    CardWithTextAndIcon(
        text = text,
        icon = { Icon(imageVector = icon, contentDescription = null, tint = iconTint) },
        modifier = Modifier.fillMaxWidth()
    )
}

private val WarningIconColor = Color(0xFFFDD835)
private val AdviceIconColor = Color(0xFF32CF5E)

@Preview("EditPasswordTitle")
@Preview("EditPasswordTitle (ru)", locale = "ru")
@Preview("EditPasswordTitle (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditPasswordTitlePreview() {
    PasswordManagerTheme {
        Surface {
            EditPasswordTitle(title = stringResource(R.string.primary_password_screen_title))
        }
    }
}
