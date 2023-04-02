package ru.dimagor555.synchronization.data.util

import java.net.DatagramSocket
import java.net.InetAddress

fun getLocalAddress(): String {
    val socket = DatagramSocket()
    socket.connect(InetAddress.getByName("8.8.8.8"), 12345)
    return socket.localAddress.hostAddress
}