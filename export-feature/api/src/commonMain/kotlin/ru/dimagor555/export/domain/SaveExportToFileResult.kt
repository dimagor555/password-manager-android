package ru.dimagor555.export.domain

sealed interface SaveExportToFileResult {

    object Success : SaveExportToFileResult

    object NoPasswords : SaveExportToFileResult

    object ExportError : SaveExportToFileResult
}