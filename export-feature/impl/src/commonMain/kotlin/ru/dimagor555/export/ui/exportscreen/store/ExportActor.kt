package ru.dimagor555.export.ui.exportscreen.store

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.export.ui.exportscreen.store.ExportStore.*
import ru.dimagor555.export.usecase.BuildExportFileNameUsecase
import ru.dimagor555.export.domain.SaveExportToFileResult
import ru.dimagor555.export.usecase.SaveExportToFileUsecase
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.res.core.MR

internal class ExportActor : Actor<State, Action, Message, SideEffect>(), KoinComponent {

    private val usecases: ExportUsecases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            is Action.ChangeFileName -> sendMessage(Message.ShowFileName(action.fileName))
            is Action.ToggleAddDateTime -> toggleAddDateTime()
            is Action.TryChooseFilePath -> tryChooseFilePath(getState())
            is Action.OnChooseFilePathSuccess -> onChooseFilePathSuccess(action.fileUri)
            is Action.OnChooseFilePathFailure -> onChooseFilePathFailure()
        }
    }

    private suspend fun toggleAddDateTime() {
        if (getState().isFormFillingInProgress) {
            sendMessage(Message.ToggleAddDateTime)
        }
    }

    private suspend fun tryChooseFilePath(state: State) {
        if (state.isFormFillingInProgress.not()) {
            return
        }
        val fileNameResult = buildExportFileName(state)
        when (fileNameResult) {
            is BuildExportFileNameUsecase.Result.PrefixIsEmpty ->
                showFileNameIsEmptyError()
            is BuildExportFileNameUsecase.Result.Success ->
                openFilePathChooser(fileNameResult.fileName)
        }
    }

    private fun buildExportFileName(state: State): BuildExportFileNameUsecase.Result =
        usecases.buildExportFileName(
            BuildExportFileNameUsecase.Params(
                prefix = state.fileName.text,
                isAddDateTime = state.isAddDateTimeToFileName,
            ),
        )

    private suspend fun showFileNameIsEmptyError() {
        val error = MR.strings.error_is_empty.desc()
        sendMessage(Message.ShowFileNameError(error))
    }

    private suspend fun openFilePathChooser(fileName: String) {
        sendMessage(Message.StartFilePathChoosing)
        sendSideEffect(SideEffect.OpenFilePathChooser(fileName))
    }

    private suspend fun onChooseFilePathSuccess(fileUri: String) {
        sendMessage(Message.StartExport)
        val result = usecases.saveExportToFile(
            SaveExportToFileUsecase.Params(fileUri),
        )
        when (result) {
            is SaveExportToFileResult.Success -> onExportSuccess()
            else -> onExportError(result)
        }
    }

    private suspend fun onExportSuccess() {
        val message = MR.strings.export_success.desc()
        sendSideEffect(SideEffect.ShowMessage(message))
        sendMessage(Message.ExitScreen)
    }

    private suspend fun onExportError(result: SaveExportToFileResult) {
        sendMessage(Message.FinishExport)
        sendMessage(Message.ShowError(error = result.toErrorMessage()))
    }

    private fun SaveExportToFileResult.toErrorMessage(): StringDesc? =
        when (this) {
            is SaveExportToFileResult.NoPasswords ->
                MR.strings.error_export_no_passwords.desc()
            is SaveExportToFileResult.ExportError ->
                MR.strings.error_during_export.desc()
            else -> null
        }

    private suspend fun onChooseFilePathFailure() {
        sendMessage(Message.FinishFilePathChoosing)
    }
}