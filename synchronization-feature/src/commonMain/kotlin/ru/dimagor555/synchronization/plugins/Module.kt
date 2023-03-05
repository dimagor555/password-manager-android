package ru.dimagor555.synchronization.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import ru.dimagor555.synchronization.plugins.configureMonitoring
import ru.dimagor555.synchronization.plugins.configureRouting

fun Application.module() {
    configureRouting()
    install(ContentNegotiation) {
        json()
    }
    configureMonitoring()
}
