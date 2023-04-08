package ru.dimagor555.masterpassword.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.data.biometric.BiometricAvailabilityRepositoryImpl
import ru.dimagor555.masterpassword.data.password.Argon2PasswordHasher
import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricAvailabilityRepository
import ru.dimagor555.masterpassword.usecase.password.repository.PasswordHasher

internal actual val masterPasswordPlatformModule = module {
    factoryOf(::Argon2PasswordHasher) bind PasswordHasher::class
    factoryOf(::BiometricAvailabilityRepositoryImpl) bind BiometricAvailabilityRepository::class
}