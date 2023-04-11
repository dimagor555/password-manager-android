package ru.dimagor555.backup

import ru.dimagor555.backup.domain.BackupType
import ru.dimagor555.backup.domain.MakeBackupResult

internal class BackupFeatureApiImpl : BackupFeatureApi {

    override suspend fun makeBackup(backupType: BackupType): MakeBackupResult {
        // TODO call make backup usecase
        return MakeBackupResult.Success
    }
}