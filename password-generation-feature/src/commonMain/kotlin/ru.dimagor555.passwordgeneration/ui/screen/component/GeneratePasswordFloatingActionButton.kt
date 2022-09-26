package ru.dimagor555.passwordgeneration.ui.screen.component

import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun GeneratePasswordFloatingActionButton(
    isEnabled: Boolean,
    onGeneratePassword: () -> Unit
) {
    FloatingActionButton(onClick = onGeneratePassword) {
        val iconAlpha = if (isEnabled) ContentAlpha.high else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides iconAlpha) {
            Icon(
                imageVector = Icons.Default.Autorenew,
                contentDescription = stringResource(MR.strings.generate_new)
            )
        }
    }
}