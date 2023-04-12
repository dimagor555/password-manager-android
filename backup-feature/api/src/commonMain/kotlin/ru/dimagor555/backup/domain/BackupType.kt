package ru.dimagor555.backup.domain

sealed interface BackupType {

    object BeforeImport : BackupType

    object BeforeSync : BackupType
}