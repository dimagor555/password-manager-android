package ru.dimagor555.ui.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> RadioTextOptionGroup(
    options: Array<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    getOptionTitle: @Composable (T) -> String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .selectableGroup()
            .height(IntrinsicSize.Min),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            RadioTextOption(
                title = getOptionTitle(option),
                isSelected = isSelected,
                onSelected = { onOptionSelected(option) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}