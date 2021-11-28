package ru.dimagor555.passwordgeneration.domain

enum class PasswordCharGroup(val chars: CharArray) {
    Digits(('0'..'9').toCharArray()),
    LowercaseLetters(('a'..'z').toCharArray()),
    UppercaseLetters(('A'..'Z').toCharArray()),
    Special("!@#$%^*-_.".toCharArray())
}

private fun CharRange.toCharArray() = this.toList().toCharArray()