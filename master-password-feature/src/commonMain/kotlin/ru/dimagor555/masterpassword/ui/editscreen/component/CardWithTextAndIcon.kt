package ru.dimagor555.masterpassword.ui.editscreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.ProvideMediumAlpha
import ru.dimagor555.res.core.MR
import androidx.compose.desktop.ui.tooling.preview.Preview
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun CardWithTextAndIcon(
    text: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = 1.dp
    ) {
        Row(
            modifier = modifier
                .padding(4.dp)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProvideMediumAlpha {
                icon()
                Text(
                    text = text,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Preview
@Composable
private fun CardWithTextAndIconPreview() {
    PasswordManagerTheme {
        Surface {
            Box(
                modifier = Modifier.padding(40.dp)
            ) {
                CardWithTextAndIcon(
                    text = stringResource(MR.strings.edit_password_screen_advice),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF00E941)
                        )
                    }
                )
            }
        }
    }
}
