package ru.dimagor555.ui.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.icerock.moko.resources.StringResource

@Composable
actual fun stringResource(res: StringResource): String {
    return stringResource(res.resourceId)
}