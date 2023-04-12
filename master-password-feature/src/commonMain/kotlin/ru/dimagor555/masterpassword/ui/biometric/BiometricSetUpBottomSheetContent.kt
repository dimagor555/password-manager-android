package ru.dimagor555.masterpassword.ui.biometric

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.MultiplatformTopBottomLayout
import ru.dimagor555.ui.core.component.button.SimpleButton
import ru.dimagor555.ui.core.component.button.SimpleTextButton
import ru.dimagor555.ui.core.util.ProvideMediumAlpha
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun BiometricSetUpBottomSheetContent(
    onEnable: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier.heightIn(max = 400.dp),
) {
    MultiplatformTopBottomLayout(
        modifier = modifier,
        topContent = {
            BiometricSetUpBottomSheetTitle()
        },
        bottomContent = {
            BiometricSetUpBottomSheetButtons(
                onEnable = onEnable,
                onCancel = onCancel,
            )
        }
    )
}

@Composable
private fun BiometricSetUpBottomSheetTitle(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(MR.strings.biometric_set_up_sheet_title),
            style = MaterialTheme.typography.h3,
        )
        ProvideMediumAlpha {
            Text(text = stringResource(MR.strings.biometric_set_up_sheet_description))
        }
    }
}

@Composable
private fun BiometricSetUpBottomSheetButtons(
    modifier: Modifier = Modifier,
    onEnable: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SimpleButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(MR.strings.enable),
            onClick = onEnable,
            contentPadding = PaddingValues(vertical = 16.dp),
        )
        SimpleTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(MR.strings.cancel),
            onClick = onCancel,
            contentPadding = PaddingValues(vertical = 16.dp),
        )
    }
}