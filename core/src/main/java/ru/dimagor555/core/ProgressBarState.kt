package ru.dimagor555.core

/*
 TODO: 04.12.2021 it's not clear why this module called "core".
       On the one hand, there are only ui states here, but from the other
       one, DataState is used in use cases and repositories. Did you get the idea?
 */

enum class ProgressBarState {
    Loading, Idle
}