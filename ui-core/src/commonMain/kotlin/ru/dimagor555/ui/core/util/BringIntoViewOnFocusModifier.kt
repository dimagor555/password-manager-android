package ru.dimagor555.ui.core.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
fun Modifier.bringIntoViewOnFocus(): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()
    val requester = remember { BringIntoViewRequester() }
    this
        .bringIntoViewRequester(requester)
        .onFocusEvent {
            if (!it.isFocused)
                return@onFocusEvent
            coroutineScope.launch {
                requester.bringIntoViewWithDelay()
            }
        }
}

@ExperimentalFoundationApi
private suspend fun BringIntoViewRequester.bringIntoViewWithDelay() {
    delay(200)
    bringIntoView()
}