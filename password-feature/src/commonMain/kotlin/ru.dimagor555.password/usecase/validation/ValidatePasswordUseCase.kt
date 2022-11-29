package ru.dimagor555.password.usecase.validation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.domain.password.findFieldError
import ru.dimagor555.password.validation.core.TextValidationError

class ValidatePasswordUseCase {
    suspend operator fun invoke(fields: PasswordFields): Map<String, TextValidationError?> =
        withContext(Dispatchers.Default) {
            fields.fields.associate {
                Pair(it.key, findFieldError(it))
            }
        }
}
