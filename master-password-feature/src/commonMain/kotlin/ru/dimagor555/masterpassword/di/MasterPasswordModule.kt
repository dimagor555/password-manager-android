package ru.dimagor555.masterpassword.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.data.biometric.BiometricSymmetricKeyRepositoryImpl
import ru.dimagor555.masterpassword.data.password.MasterPasswordHashRepositoryImpl
import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordUseCases
import ru.dimagor555.masterpassword.ui.loginscreen.store.LoginUseCases
import ru.dimagor555.masterpassword.usecase.biometric.CanUseBiometricLoginUsecase
import ru.dimagor555.masterpassword.usecase.biometric.IsBiometricAvailableUsecase
import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricSymmetricKeyRepository
import ru.dimagor555.masterpassword.usecase.password.HasMasterPasswordUsecase
import ru.dimagor555.masterpassword.usecase.password.LoginByPasswordUseCase
import ru.dimagor555.masterpassword.usecase.password.SetMasterPasswordUseCase
import ru.dimagor555.masterpassword.usecase.password.ValidatePasswordUseCase
import ru.dimagor555.masterpassword.usecase.password.repository.MasterPasswordHashRepository

val masterPasswordModule = module {
    includes(masterPasswordPlatformModule)

    singleOf(::MasterPasswordHashRepositoryImpl) bind MasterPasswordHashRepository::class
    singleOf(::BiometricSymmetricKeyRepositoryImpl) bind BiometricSymmetricKeyRepository::class

    factoryOf(::LoginUseCases)
    factoryOf(::EditMasterPasswordUseCases)

    factoryOf(::HasMasterPasswordUsecase)
    factoryOf(::SetMasterPasswordUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::LoginByPasswordUseCase)

    factoryOf(::IsBiometricAvailableUsecase)
    factoryOf(::CanUseBiometricLoginUsecase)
}