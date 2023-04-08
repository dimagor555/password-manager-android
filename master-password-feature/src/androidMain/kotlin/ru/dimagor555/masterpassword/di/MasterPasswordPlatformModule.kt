package ru.dimagor555.masterpassword.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.data.biometric.BiometricAvailabilityRepositoryImpl
import ru.dimagor555.masterpassword.data.biometric.BiometricCipherRepositoryImpl
import ru.dimagor555.masterpassword.data.biometric.BiometricSecretKeyProvider
import ru.dimagor555.masterpassword.data.password.Argon2PasswordHasher
import ru.dimagor555.masterpassword.usecase.LoginByBiometricUsecase
import ru.dimagor555.masterpassword.usecase.SetUpBiometricLoginUsecase
import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricAvailabilityRepository
import ru.dimagor555.masterpassword.usecase.password.repository.PasswordHasher
import ru.dimagor555.masterpassword.usecase.repository.BiometricCipherRepository

internal actual val masterPasswordPlatformModule = module {
    factory<PasswordHasher> { Argon2PasswordHasher() }
    factoryOf(::BiometricAvailabilityRepositoryImpl) bind BiometricAvailabilityRepository::class
    factoryOf(::BiometricSecretKeyProvider)
    factoryOf(::BiometricCipherRepositoryImpl) bind BiometricCipherRepository::class

    factoryOf(::LoginByBiometricUsecase)
    factoryOf(::SetUpBiometricLoginUsecase)
}