package ru.dimagor555.masterpassword.ui.startscreen

import com.arkivanov.decompose.ComponentContext

class WelcomeComponent constructor(
    componentContext: ComponentContext,
    private val onNavigateNext: () -> Unit
) : ComponentContext by componentContext, Welcome {

    fun navigateNext() {
        onNavigateNext()
    }

}
