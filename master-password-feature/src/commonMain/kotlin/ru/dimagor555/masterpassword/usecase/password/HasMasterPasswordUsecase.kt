package ru.dimagor555.masterpassword.usecase.password

import ru.dimagor555.masterpassword.usecase.password.repository.MasterPasswordHashRepository

// TODO extract to feature api
class HasMasterPasswordUsecase(
    private val masterPasswordHashRepository: MasterPasswordHashRepository,
) {

    suspend operator fun invoke(): Boolean =
        masterPasswordHashRepository.hasPasswordHash()
}