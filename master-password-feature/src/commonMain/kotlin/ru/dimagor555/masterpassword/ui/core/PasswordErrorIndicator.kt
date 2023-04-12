package ru.dimagor555.masterpassword.ui.core

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.masterpassword.ui.core.model.PasswordErrorIndicatorColors
import ru.dimagor555.masterpassword.ui.core.model.ThemePasswordErrorIndicatorColors
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import androidx.compose.desktop.ui.tooling.preview.Preview

@Composable
fun PasswordErrorIndicator(
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = PasswordErrorIndicatorColors.chooseColors(
        isError = isError,
        isLightTheme = MaterialTheme.colors.isLight
    )
    PasswordErrorIndicator(
        colors = colors,
        modifier = modifier
    )
}

@Composable
private fun PasswordErrorIndicator(
    colors: PasswordErrorIndicatorColors,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.requiredSize(128.dp),
        shape = RoundedCornerShape(20.dp),
        contentColor = colors.lockColor,
        color = colors.bgColor
    ) {
        Icon(
            modifier = Modifier.requiredSize(72.dp),
            imageVector = Icons.Default.Lock,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun DefaultPasswordIndicatorPreview() {
    PasswordManagerTheme {
        Surface(modifier = Modifier.size(256.dp)) {
            PasswordErrorIndicator(
                colors = ThemePasswordErrorIndicatorColors.NoError.getColors(
                    MaterialTheme.colors.isLight
                )
            )
        }
    }
}

@Preview
@Composable
private fun ErrorPasswordIndicatorPreview() {
    PasswordManagerTheme {
        Surface(modifier = Modifier.size(256.dp)) {
            PasswordErrorIndicator(
                colors = ThemePasswordErrorIndicatorColors.Error.getColors(
                    MaterialTheme.colors.isLight
                )
            )
        }
    }
}