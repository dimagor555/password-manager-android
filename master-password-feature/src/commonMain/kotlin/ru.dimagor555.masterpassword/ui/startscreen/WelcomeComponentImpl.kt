package ru.dimagor555.masterpassword.ui.startscreen

import com.arkivanov.decompose.ComponentContext

class WelcomeComponentImpl constructor(
    componentContext: ComponentContext,
    private val onNavigateNext: () -> Unit
) : ComponentContext by componentContext, WelcomeComponent {

    fun navigateNext() {
        onNavigateNext()
    }

}
