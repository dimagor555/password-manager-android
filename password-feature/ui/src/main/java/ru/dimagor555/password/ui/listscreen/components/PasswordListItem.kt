package ru.dimagor555.password.ui.listscreen.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.ui.listscreen.model.PasswordState
import ru.dimagor555.ui.core.component.SingleLineText
import ru.dimagor555.ui.core.component.button.SimpleIconButton
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.ProvideMediumAlpha
import ru.dimagor555.password.ui.R

@Composable
internal fun PasswordListItem(
    passwordState: PasswordState,
    onToggleFavourite: (id: Int) -> Unit,
    onCopyPassword: (id: Int) -> Unit,
    onPasswordSelected: (id: Int) -> Unit
) {
    PasswordCard(onPasswordSelected = { onPasswordSelected(passwordState.id) }) {
        PasswordContent(
            modifier = Modifier.weight(1f),
            passwordState = passwordState,
            onToggleFavourite = onToggleFavourite
        )
        Spacer(modifier = Modifier.width(12.dp))
        SimpleIconButton(
            icon = Icons.Default.ContentCopy,
            contentDescription = stringResource(R.string.copy_password),
            onClick = { onCopyPassword(passwordState.id) }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PasswordCard(
    onPasswordSelected: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onPasswordSelected
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Composable
private fun PasswordContent(
    modifier: Modifier = Modifier,
    passwordState: PasswordState,
    onToggleFavourite: (id: Int) -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PasswordTitleWithStarRow(
            title = passwordState.title,
            isFavourite = passwordState.isFavourite,
            onToggleFavourite = { onToggleFavourite(passwordState.id) }
        )
        ProvideMediumAlpha {
            SingleLineText(
                text = passwordState.login,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
private fun PasswordTitleWithStarRow(
    title: String,
    isFavourite: Boolean,
    onToggleFavourite: () -> Unit
) {
    Row(
        modifier = Modifier.clickable { onToggleFavourite() },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SingleLineText(
            modifier = Modifier.weight(weight = 1f, fill = false),
            text = title,
            style = MaterialTheme.typography.subtitle1
        )
        FavouriteIcon(
            modifier = Modifier.weight(weight = 0.1f, fill = false),
            isFavourite = isFavourite
        )
    }
}

@Preview("Password list item")
@Preview("Password list item (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PasswordListItemPreview() {
    PasswordManagerTheme {
        Surface {
            Column(modifier = Modifier.padding(8.dp)) {
                PasswordListItem(
                    passwordState = PasswordState(
                        id = 0,
                        title = "Very very very long service name",
                        login = "Very long and beautiful username login user",
                        isFavourite = false
                    ),
                    onToggleFavourite = {},
                    onCopyPassword = {},
                    onPasswordSelected = {}
                )
                Spacer(modifier = Modifier.height(8.dp))
                PasswordListItem(
                    passwordState = PasswordState(
                        id = 0,
                        title = "Yandex",
                        login = "Ivan Ivanov",
                        isFavourite = true
                    ),
                    onToggleFavourite = {},
                    onCopyPassword = {},
                    onPasswordSelected = {}
                )
            }
        }
    }
}
