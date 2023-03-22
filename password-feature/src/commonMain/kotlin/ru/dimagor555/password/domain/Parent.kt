package ru.dimagor555.password.domain

interface Parent : Child {
    override val id: String?
    val children: Set<Child>
}
