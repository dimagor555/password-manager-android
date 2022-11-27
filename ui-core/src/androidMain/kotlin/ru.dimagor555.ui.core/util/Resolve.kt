package ru.dimagor555.ui.core.util

import dev.icerock.moko.resources.desc.StringDesc
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun StringDesc.resolve(): String = this.toString(context = LocalContext.current)