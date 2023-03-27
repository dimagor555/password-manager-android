package ru.dimagor555.ui.core.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import androidx.compose.desktop.ui.tooling.preview.Preview

@Composable
fun <T> ButtonGroup(
    values: Array<T>,
    selectedValue: T,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    content: @Composable (T) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        values.forEachIndexed { index, value ->
            val isSelected = value == selectedValue
            OutlinedButton(
                onClick = {
                    if (!isSelected)
                        onValueChange(value)
                },
                modifier = Modifier
                    .weight(1f)
                    .offsetByIndex(index)
                    .zIndexBySelection(isSelected),
                shape = getShapeByIndex(index, values.size, cornerRadius),
                elevation = if (isSelected) selectedButtonElevation() else null,
                contentPadding = PaddingValues(vertical = 4.dp, horizontal = 4.dp)
            ) {
                content(value)
            }
        }
    }
}

private fun getShapeByIndex(index: Int, size: Int, radius: Dp) =
    when (index) {
        0 -> RoundedCornerShape(topStart = radius, bottomStart = radius)
        size - 1 -> RoundedCornerShape(topEnd = radius, bottomEnd = radius)
        else -> RoundedCornerShape(0.dp)
    }

private fun Modifier.offsetByIndex(index: Int) =
    offset(
        x = (-1 * index).dp,
        y = 0.dp
    )

private fun Modifier.zIndexBySelection(isSelected: Boolean) =
    zIndex(
        if (isSelected) 1f
        else 0f
    )

@Composable
private fun selectedButtonElevation() = ButtonDefaults.elevation(
    defaultElevation = 3.dp,
    pressedElevation = 9.dp,
    disabledElevation = 1.dp,
    hoveredElevation = 5.dp,
    focusedElevation = 5.dp,
)


@Preview
@Composable
private fun ButtonGroupPreview() {
    PasswordManagerTheme {
        ButtonGroup(
            values = arrayOf(1, 2, 3),
            selectedValue = 2,
            onValueChange = {}
        ) {
            Text(text = "$it")
        }
    }
}