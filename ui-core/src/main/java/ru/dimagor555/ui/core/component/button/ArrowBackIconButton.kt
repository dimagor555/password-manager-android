package ru.dimagor555.ui.core.component.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dimagor555.ui.core.R

@Composable
fun ArrowBackIconButton(onClick: () -> Unit) {
    SimpleIconButton(
        icon = Icons.Default.ArrowBack,
        contentDescription = stringResource(R.string.back),
        onClick = onClick
    )
}