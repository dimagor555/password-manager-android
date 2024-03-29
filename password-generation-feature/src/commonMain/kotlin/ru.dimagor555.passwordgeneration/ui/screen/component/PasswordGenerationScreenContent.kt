package ru.dimagor555.passwordgeneration.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.Action
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.State
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview

@Composable
internal fun PasswordGenerationScreenContent(
    state: State,
    sendAction: (Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GeneratedPasswordText(text = state.password)
        PasswordGenerationSettings(
            state = state,
            onChangeLength = { sendAction(Action.ChooseLength(it)) },
            onToggleCharGroup = { sendAction(Action.ToggleCharGroup(it)) }
        )
    }
}

@Preview
@Composable
private fun PasswordGenerationScreenContentPreview() {
    PasswordManagerTheme {
        Surface {
            PasswordGenerationScreenContent(
                state = State(),
                sendAction = {}
            )
        }
    }
}