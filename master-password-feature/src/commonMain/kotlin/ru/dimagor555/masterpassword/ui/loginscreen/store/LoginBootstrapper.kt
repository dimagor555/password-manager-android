package ru.dimagor555.masterpassword.ui.loginscreen.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.masterpassword.ui.loginscreen.store.LoginStore.Action
import ru.dimagor555.masterpassword.ui.loginscreen.store.LoginStore.Message
import ru.dimagor555.masterpassword.usecase.biometric.CanUseBiometricLoginUsecase
import ru.dimagor555.mvicompose.abstraction.Bootstrapper

internal class LoginBootstrapper : Bootstrapper<Action, Message>(), KoinComponent {

    private val canUseBiometricLogin: CanUseBiometricLoginUsecase by inject()

    override fun init(scope: CoroutineScope) {
        scope.launch {
            if (canUseBiometricLogin()) {
                sendMessage(Message.EnableBiometricLogin)
            }
        }
    }
}