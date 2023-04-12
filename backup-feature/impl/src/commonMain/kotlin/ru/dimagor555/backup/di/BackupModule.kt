package ru.dimagor555.backup.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.backup.BackupFeatureApi
import ru.dimagor555.backup.BackupFeatureApiImpl

val backupModule = module {
    singleOf(::BackupFeatureApiImpl) bind BackupFeatureApi::class
}