package ru.dimagor555.synchronization.data.rest.repository

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository

internal class ClientRepositoryImpl : ClientRepository {

    private var _client: HttpClient? = null

    override val client: HttpClient
        get() = _client ?: createClient()

    private fun createClient(): HttpClient {
        _client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        return _client!!
    }

    override fun closeClient() {
        _client?.close()
        _client = null
    }
}