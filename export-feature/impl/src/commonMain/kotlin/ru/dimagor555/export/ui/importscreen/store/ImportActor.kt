package ru.dimagor555.export.ui.importscreen.store

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.export.ui.importscreen.store.ImportStore.*
import ru.dimagor555.export.usecase.LoadExportFromFileUsecase
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.res.core.MR

internal class ImportActor : Actor<State, Action, Message, SideEffect>(), KoinComponent {

    private val usecases: ImportUsecases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            is Action.TryChooseFile -> tryChooseFile(getState())
            is Action.OnChooseFileSuccess -> onChooseFileSuccess(action.fileUri)
            is Action.OnChooseFileFailure -> onChooseFileFailure()
            is Action.ToggleClearBeforeImport -> toggleClearBeforeImport()
            is Action.ToggleMakeBackup -> toggleMakeBackup()
            is Action.TryImport -> tryImport(getState())
        }
    }

    private suspend fun tryChooseFile(state: State) {
        if (state.isFormFillingInProgress.not()) {
            return
        }
        sendMessage(Message.StartFileChoosing)
        sendSideEffect(SideEffect.OpenFileChooser)
    }

    private suspend fun onChooseFileSuccess(fileUri: String) {
        sendMessage(
            Message.ShowSelectedFile(
                uri = fileUri,
                pathOrName = usecases.getFilePathOrName(fileUri),
            ),
        )
        sendMessage(Message.FinishFileChoosing)
    }

    private suspend fun onChooseFileFailure() {
        sendMessage(Message.FinishFileChoosing)
    }

    private suspend fun toggleClearBeforeImport() {
        sendMessageIfFormFillingInProgress(Message.ToggleClearBeforeImport)
    }

    private suspend fun sendMessageIfFormFillingInProgress(message: Message) {
        if (getState().isFormFillingInProgress) {
            sendMessage(message)
        }
    }

    private suspend fun toggleMakeBackup() {
        sendMessageIfFormFillingInProgress(Message.ToggleMakeBackup)
    }

    private suspend fun tryImport(state: State) {
        if (state.isFormFillingInProgress.not()) {
            return
        }
        if (state.isFileNotSelected) {
            return showFileNotSelectedMessage()
        }
        import(state)
    }

    private suspend fun showFileNotSelectedMessage() {
        val message = MR.strings.import_file_not_selected.desc()
        sendMessage(Message.ShowError(message))
    }

    private suspend fun import(state: State) {
        sendMessage(Message.StartImport)
        val result = usecases.loadExportFromFile(
            LoadExportFromFileUsecase.Params(
                fileUri = state.selectedFileUri,
                isClearBeforeImport = state.isClearBeforeImport,
                isMakeBackup = state.isMakeBackup,
            )
        )
        when (result) {
            is LoadExportFromFileUsecase.Result.Success -> onImportSuccess()
            else -> onImportError(result)
        }
    }

    private suspend fun onImportSuccess() {
        val message = MR.strings.import_success.desc()
        sendSideEffect(SideEffect.ShowMessage(message))
        sendMessage(Message.ExitScreen)
    }

    private suspend fun onImportError(result: LoadExportFromFileUsecase.Result) {
        sendMessage(Message.FinishImport)
        sendMessage(Message.ShowError(error = result.toErrorMessage()))
    }

    private fun LoadExportFromFileUsecase.Result.toErrorMessage(): StringDesc? =
        when (this) {
            is LoadExportFromFileUsecase.Result.Success ->
                null
            is LoadExportFromFileUsecase.Result.CouldNotReadFile ->
                MR.strings.error_could_not_read_file.desc()
            is LoadExportFromFileUsecase.Result.InvalidFileFormat ->
                MR.strings.error_invalid_file_format.desc()
            is LoadExportFromFileUsecase.Result.CouldNotMakeBackup ->
                MR.strings.error_could_not_make_backup.desc()
            is LoadExportFromFileUsecase.Result.ImportError ->
                MR.strings.error_during_import.desc()
        }
}