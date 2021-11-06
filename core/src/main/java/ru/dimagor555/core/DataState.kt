package ru.dimagor555.core

sealed class DataState<T> {
    data class Data<T>(
        val data: T
    ) : DataState<T>()

    class Loading<T> : DataState<T>()
}