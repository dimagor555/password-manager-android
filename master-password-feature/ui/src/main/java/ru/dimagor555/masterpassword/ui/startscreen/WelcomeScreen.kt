package ru.dimagor555.masterpassword.ui.startscreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.masterpassword.ui.core.PasswordErrorIndicator
import ru.dimagor555.ui.core.component.button.SimpleButton
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.ProvideMediumAlpha
import ru.dimagor555.masterpassword.ui.R

@Composable
fun WelcomeScreen(
    onNavigateNext: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppName(modifier = Modifier.weight(1f))
            LogoAndDescription(modifier = Modifier.weight(1f))
            DataStorageAndServerDisclaimer()
            SimpleButton(
                text = stringResource(R.string.start),
                onClick = onNavigateNext
            )
        }
    }
}

@Composable
private fun AppName(modifier: Modifier = Modifier) {
    CenterColumnWithLargeSpacing(modifier = modifier) {
        val appNameWords = stringArrayResource(id = R.array.app_name_words)
        val wordColors = MaterialTheme.colors.run { arrayOf(primary, secondary) }
        appNameWords.forEachIndexed { index, word ->
            val color = wordColors[index % wordColors.size]
            Text(
                text = word,
                style = MaterialTheme.typography.h1,
                color = color
            )
        }
    }
}

@Composable
private fun CenterColumnWithLargeSpacing(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        content = content
    )
}

@Composable
private fun LogoAndDescription(modifier: Modifier = Modifier) {
    CenterColumnWithLargeSpacing(modifier = modifier) {
        PasswordErrorIndicator(isError = false)
        Text(
            text = stringResource(R.string.welcome_screen_description),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DataStorageAndServerDisclaimer() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ProvideMediumAlpha {
            val disclaimers = arrayOf(
                stringResource(R.string.data_storage_disclaimer),
                stringResource(R.string.server_disclaimer)
            )
            disclaimers.forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Preview("WelcomeScreen", device = Devices.NEXUS_5)
@Preview("WelcomeScreen (ru)", locale = "ru")
@Preview("WelcomeScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WelcomeScreenPreview() {
    PasswordManagerTheme {
        WelcomeScreen {}
    }
}
