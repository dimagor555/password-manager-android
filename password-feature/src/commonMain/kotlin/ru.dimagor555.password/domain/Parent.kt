package ru.dimagor555.password.domain

interface Parent : Child {
    val children: Set<Child>
}