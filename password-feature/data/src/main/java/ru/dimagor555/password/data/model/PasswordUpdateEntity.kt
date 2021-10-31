package ru.dimagor555.password.data.model

import ru.dimagor555.password.domain.Password

internal class PasswordUpdateEntity(passwordEntity: PasswordEntity) {
    val passwordModel = passwordEntity.passwordModel

    val usageModelsToInsert = passwordEntity.usageModels.filter { it.id == null }
    val usageModelsToUpdate = passwordEntity.usageModels.filter { it.id != null }
}

internal fun Password.toPasswordUpdateEntity() = PasswordUpdateEntity(this.toPasswordEntity())