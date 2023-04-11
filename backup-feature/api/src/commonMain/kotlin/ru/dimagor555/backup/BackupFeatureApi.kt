package ru.dimagor555.backup

interface BackupFeatureApi {

    sealed interface BackupType {
        object BeforeImport : BackupType
        object BeforeSync : BackupType
    }

    suspend fun makeBackup(backupType: BackupType)
}