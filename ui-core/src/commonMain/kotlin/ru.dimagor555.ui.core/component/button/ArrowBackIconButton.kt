package ru.dimagor555.ui.core.component.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import ru.dimagor555.ui.core.MR
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun ArrowBackIconButton(onClick: () -> Unit) {
    SimpleIconButton(
        icon = Icons.Default.ArrowBack,
        contentDescription = stringResource(MR.strings.back),
        onClick = onClick
    )
}
