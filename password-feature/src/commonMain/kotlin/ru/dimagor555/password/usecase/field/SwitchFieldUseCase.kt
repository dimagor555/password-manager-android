package ru.dimagor555.password.usecase.field

import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.domain.password.addFieldByKey
import ru.dimagor555.password.domain.password.deleteFieldByKey

class SwitchFieldUseCase {

    data class Params(
        val initialKey: String,
        val newKey: String,
        val passwordFields: PasswordFields,
    )

    operator fun invoke(params: Params): Result = with(params) {
        val hasBothKeys = checkForNullableFields(params)
        if (hasBothKeys) return Result.SetContainsBothFields
        return replaceOrAddField(params)
    }

    private fun checkForNullableFields(params: Params): Boolean {
        val keys = params.passwordFields.fields.map { it.key }
        return params.initialKey in keys && params.newKey in keys
    }

    private fun replaceOrAddField(params: Params): Result = with(params) {
        val initialField = passwordFields.fields.find { it.key == initialKey }
            ?: return Result.FieldNotExist

        val updatedFields = if (initialField.text.isEmpty()) {
            replaceField(params)
        } else {
            params.passwordFields.addFieldByKey(params.newKey)
        }
        return Result.Success(updatedFields)
    }

    private fun replaceField(params: Params): PasswordFields {
        val fields = params.passwordFields.deleteFieldByKey(params.initialKey)
        return fields.addFieldByKey(params.newKey)
    }

    sealed interface Result {
        data class Success(val fields: PasswordFields) : Result
        object FieldNotExist : Result
        object SetContainsBothFields : Result
    }
}
