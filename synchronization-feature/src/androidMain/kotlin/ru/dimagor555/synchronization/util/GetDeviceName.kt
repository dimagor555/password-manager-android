package ru.dimagor555.synchronization.util

import android.os.Build

actual fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL

    return "$manufacturer $model"
}