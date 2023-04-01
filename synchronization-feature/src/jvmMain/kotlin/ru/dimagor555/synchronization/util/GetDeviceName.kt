package ru.dimagor555.synchronization.util

import java.net.InetAddress

actual fun getDeviceName(): String {
    val localHost = InetAddress.getLocalHost()
    return localHost.hostName
}