package ru.dimagor555.synchronization.usecase.rest.repository

interface ServerRepository {
    fun createServer()

    fun stopServer()
}
