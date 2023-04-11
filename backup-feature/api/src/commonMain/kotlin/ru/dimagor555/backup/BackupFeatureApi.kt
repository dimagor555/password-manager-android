package ru.dimagor555.backup

import ru.dimagor555.backup.domain.BackupType
import ru.dimagor555.backup.domain.MakeBackupResult

interface BackupFeatureApi {

    suspend fun makeBackup(backupType: BackupType): MakeBackupResult
}