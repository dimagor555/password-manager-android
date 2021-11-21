package ru.dimagor555.password.exception

class PasswordNotExistsException(passwordId: Int) :
    RuntimeException("Password with id=$passwordId does not exist")