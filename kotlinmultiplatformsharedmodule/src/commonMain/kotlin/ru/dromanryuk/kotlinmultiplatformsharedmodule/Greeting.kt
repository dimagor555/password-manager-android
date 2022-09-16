package ru.dromanryuk.kotlinmultiplatformsharedmodule

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}