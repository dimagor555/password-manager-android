package ru.dimagor555.synchronization.server.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.module() {
    configureRouting()
    install(ContentNegotiation) {
        json()
    }
    configureMonitoring()
}
