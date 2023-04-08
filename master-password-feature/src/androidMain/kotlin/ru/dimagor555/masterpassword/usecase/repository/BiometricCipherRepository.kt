package ru.dimagor555.masterpassword.usecase.repository

import javax.crypto.Cipher

internal interface BiometricCipherRepository {

    fun getCipherForEncryption(): Cipher

    suspend fun encrypt(cipher: Cipher, plaintext: String): String

    fun getCipherForDecryption(ciphertext: String): Cipher

    suspend fun decrypt(cipher: Cipher, ciphertext: String): String
}