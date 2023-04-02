package ru.dimagor555.synchronization.usecase.rest.repository

import io.ktor.client.*

interface ClientRepository {

    val client: HttpClient

    fun closeClient()
}