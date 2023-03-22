package ru.dimagor555.password.usecase.field

import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.domain.password.addFieldByKey

class AddFieldUseCase {

    data class Params(
        val key: String,
        val fields: PasswordFields,
    )

    operator fun invoke(params: Params): PasswordFields =
        params.fields.addFieldByKey(params.key)

}
