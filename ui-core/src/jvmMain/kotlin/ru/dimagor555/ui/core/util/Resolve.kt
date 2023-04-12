package ru.dimagor555.ui.core.util

import dev.icerock.moko.resources.desc.StringDesc

actual fun StringDesc.resolve(): String = this.localized()