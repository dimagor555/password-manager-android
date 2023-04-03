package ru.dimagor555.export.ui.importscreen.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.dimagor555.res.core.MR
import javax.swing.JFileChooser
import javax.swing.UIManager
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
internal actual fun ImportFileChooser(
    isVisible: Boolean,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit
) {
    LaunchedEffect(isVisible) {
        if (isVisible.not()) return@LaunchedEffect
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        val fileChooser = JFileChooser().apply {
            dialogTitle = MR.strings.choose_import_file.localized()
            isMultiSelectionEnabled = false
            isAcceptAllFileFilterUsed = true
            fileFilter = FileNameExtensionFilter(
                "Password export file",
                "pass",
            ).also { addChoosableFileFilter(it) }
        }
        val result = fileChooser.showOpenDialog(null)
        when (result) {
            JFileChooser.APPROVE_OPTION -> onSuccess(fileChooser.selectedFile.toString())
            else -> onFailure()
        }
    }
}