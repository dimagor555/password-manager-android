package ru.dimagor555.encryption.domain

import javax.crypto.SecretKey

interface CryptoKey {
    val secretKey: SecretKey
}