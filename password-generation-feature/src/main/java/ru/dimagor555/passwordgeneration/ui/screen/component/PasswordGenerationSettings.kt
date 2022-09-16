package ru.dimagor555.passwordgeneration.ui.screen.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.passwordgeneration.domain.PasswordCharGroup
import ru.dimagor555.passwordgeneration.domain.PasswordLength
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.State
import ru.dimagor555.passwordgeneration.R
import ru.dimagor555.ui.core.component.button.ButtonGroup
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun PasswordGenerationSettings(
    state: State,
    onChangeLength: (PasswordLength) -> Unit,
    onToggleCharGroup: (PasswordCharGroup) -> Unit
) {
    LengthSetting(length = state.length, onChangeLength = onChangeLength)
    CharGroupSettings(
        selectedCharGroups = state.selectedCharGroups,
        onToggleCharGroup = onToggleCharGroup
    )
}

@Composable
private fun LengthSetting(
    length: PasswordLength,
    onChangeLength: (PasswordLength) -> Unit
) {
    SettingRow(name = stringResource(R.string.length)) {
        ButtonGroup(
            values = PasswordLength.values(),
            selectedValue = length,
            onValueChange = onChangeLength,
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            Text(text = "${it.value}")
        }
    }
}

@Composable
private fun CharGroupSettings(
    selectedCharGroups: List<PasswordCharGroup>,
    onToggleCharGroup: (PasswordCharGroup) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PasswordCharGroup.values().forEach {
            val isSelected = it in selectedCharGroups
            CharGroupSetting(
                name = getCharGroupName(it),
                isSelected = isSelected,
                onToggle = { onToggleCharGroup(it) }
            )
        }
    }
}

@Composable
private fun getCharGroupName(charGroup: PasswordCharGroup) = stringResource(
    when (charGroup) {
        PasswordCharGroup.Digits -> R.string.char_group_name_digits
        PasswordCharGroup.LowercaseLetters -> R.string.char_group_name_lowercase
        PasswordCharGroup.UppercaseLetters -> R.string.char_group_name_uppercase
        PasswordCharGroup.Special -> R.string.char_group_name_special
    }
)

@Composable
private fun CharGroupSetting(
    name: String,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    SettingRow(name = name) {
        Switch(checked = isSelected, onCheckedChange = { onToggle() })
    }
}

@Preview("PasswordGenerationSettings")
@Preview("PasswordGenerationSettings (ru)", locale = "ru")
@Preview("PasswordGenerationSettings (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordGenerationSettingsPreview() {
    PasswordManagerTheme {
        androidx.compose.material.Surface {
            Column {
                PasswordGenerationSettings(
                    state = State(),
                    onChangeLength = {}
                ) {}
            }
        }
    }
}
