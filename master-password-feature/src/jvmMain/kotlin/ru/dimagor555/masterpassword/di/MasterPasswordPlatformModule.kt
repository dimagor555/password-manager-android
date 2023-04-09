package ru.dimagor555.masterpassword.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.data.biometric.BiometricAvailabilityRepositoryImpl
import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricAvailabilityRepository

internal actual val masterPasswordPlatformModule = module {
    factoryOf(::BiometricAvailabilityRepositoryImpl) bind BiometricAvailabilityRepository::class
}