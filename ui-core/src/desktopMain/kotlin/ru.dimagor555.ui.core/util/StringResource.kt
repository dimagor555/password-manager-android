package ru.dimagor555.ui.core.util

import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.resources.StringResource

actual fun stringResource(res: StringResource): String {
    return res.desc().localized()
}