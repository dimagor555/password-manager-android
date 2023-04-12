package ru.dimagor555.backup.domain

sealed interface MakeBackupResult {

    object Success : MakeBackupResult
}