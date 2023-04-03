package ru.dimagor555.export.usecase

import kotlinx.datetime.Clock
import ru.dimagor555.export.domain.ExportFileNameBuilder

internal class BuildExportFileNameUsecase {

    data class Params(
        val prefix: String,
        val isAddDateTime: Boolean,
    )

    operator fun invoke(params: Params): Result {
        if (params.prefix.isEmpty()) {
            return Result.PrefixIsEmpty
        }
        val fileName = ExportFileNameBuilder(
            prefix = params.prefix,
            instant = if (params.isAddDateTime) Clock.System.now() else null,
        ).build()
        return Result.Success(fileName)
    }

    sealed interface Result {

        data class Success(val fileName: String) : Result

        object PrefixIsEmpty : Result
    }
}