package ru.dimagor555.export.ui.exportscreen.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.dimagor555.res.core.MR
import java.io.File
import javax.swing.JFileChooser
import javax.swing.UIManager

@Composable
internal actual fun ExportFilePathChooser(
    fileName: String?,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit,
) {
    LaunchedEffect(fileName) {
        fileName ?: return@LaunchedEffect
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        val fileChooser = JFileChooser().apply {
            dialogTitle = MR.strings.choose_export_file_path.localized()
            selectedFile = File(fileName)
        }
        val result = fileChooser.showSaveDialog(null)
        when (result) {
            JFileChooser.APPROVE_OPTION -> onSuccess(fileChooser.selectedFile.toString())
            else -> onFailure()
        }
    }
}