package ru.dimagor555.core.mvi.abstraction

interface Reducer<State : Any, in Message : Any> {
    fun State.reduce(msg: Message): State
}