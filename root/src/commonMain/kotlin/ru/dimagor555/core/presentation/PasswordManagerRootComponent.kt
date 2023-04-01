package ru.dimagor555.core.presentation

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.core.presentation.RootComponent.Child.*
import ru.dimagor555.core.presentation.model.Config
import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import ru.dimagor555.masterpassword.ui.editscreen.EditMasterPasswordComponent
import ru.dimagor555.masterpassword.ui.editscreen.EditMasterPasswordComponent.EditMasterPasswordCallbacks
import ru.dimagor555.masterpassword.ui.editscreen.createEditMasterPasswordComponent
import ru.dimagor555.masterpassword.ui.loginscreen.LoginComponent
import ru.dimagor555.masterpassword.ui.loginscreen.createLoginComponent
import ru.dimagor555.masterpassword.ui.startscreen.WelcomeComponent
import ru.dimagor555.masterpassword.ui.startscreen.WelcomeComponentImpl
import ru.dimagor555.password.ui.createscreen.CreatePasswordComponent
import ru.dimagor555.password.ui.createscreen.CreatePasswordComponent.CreatePasswordComponentCallbacks
import ru.dimagor555.password.ui.createscreen.createCreatePasswordComponent
import ru.dimagor555.password.ui.detailsscreen.PasswordDetailsComponent
import ru.dimagor555.password.ui.detailsscreen.PasswordDetailsComponent.PasswordDetailsComponentCallbacks
import ru.dimagor555.password.ui.detailsscreen.createPasswordDetailsComponent
import ru.dimagor555.password.ui.editscreen.EditPasswordComponent
import ru.dimagor555.password.ui.editscreen.EditPasswordComponent.EditPasswordComponentCallbacks
import ru.dimagor555.password.ui.editscreen.createEditPasswordComponent
import ru.dimagor555.password.ui.listscreen.PasswordListComponent
import ru.dimagor555.password.ui.listscreen.PasswordListComponent.PasswordListComponentCallbacks
import ru.dimagor555.password.ui.listscreen.createPasswordListComponent
import ru.dimagor555.passwordgeneration.ui.screen.PasswordGenerationComponent
import ru.dimagor555.passwordgeneration.ui.screen.PasswordGenerationComponent.Result.GeneratedPassword
import ru.dimagor555.passwordgeneration.ui.screen.createPasswordGenerationComponent
import ru.dimagor555.synchronization.ui.deviceslistscreen.DevicesListComponent
import ru.dimagor555.synchronization.ui.deviceslistscreen.DevicesListComponent.DevicesListComponentCallbacks
import ru.dimagor555.synchronization.ui.deviceslistscreen.createDevicesListComponent
import ru.dimagor555.synchronization.ui.resultsyncscreen.ResultSyncComponent
import ru.dimagor555.synchronization.ui.resultsyncscreen.ResultSyncComponent.ResultSyncComponentCallbacks
import ru.dimagor555.synchronization.ui.resultsyncscreen.createResultSyncComponent
import ru.dimagor555.synchronization.ui.syncscreen.SyncComponent
import ru.dimagor555.synchronization.ui.syncscreen.SyncComponent.SyncComponentCallbacks
import ru.dimagor555.synchronization.ui.syncscreen.createSyncComponent

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Welcome(val component: WelcomeComponent) : Child()
        class Login(val component: LoginComponent) : Child()
        class Generation(val component: PasswordGenerationComponent) : Child()
        class EditMaster(val component: EditMasterPasswordComponent) : Child()
        class PasswordList(val component: PasswordListComponent) : Child()
        class EditPassword(val component: EditPasswordComponent) : Child()
        class PasswordDetails(val component: PasswordDetailsComponent) : Child()
        class CreatePassword(val component: CreatePasswordComponent) : Child()
        class DevicesList(val component: DevicesListComponent) : Child()
        class Sync(val component: SyncComponent) : Child()
        class ResultSync(val component: ResultSyncComponent) : Child()
    }
}

class PasswordManagerRootComponent(
    componentContext: ComponentContext,
) : RootComponent, KoinComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val componentScope by componentScope()

    private val masterPasswordRepository: MasterPasswordRepository by inject()

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = determineStartDestination(),
            handleBackButton = true,
            childFactory = ::createChild,
        )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = _childStack

    private val generatedPassword = MutableSharedFlow<GeneratedPassword>(
        extraBufferCapacity = Int.MAX_VALUE,
    )

    init {
        observeGeneratedPassword()
    }

    private fun determineStartDestination(): Config = runBlocking {
        when (masterPasswordRepository.hasPassword()) {
            true -> Config.Login
            else -> Config.Welcome
        }
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (config) {
            is Config.Welcome -> Welcome(welcome(componentContext))
            is Config.Login -> Login(login(componentContext))
            is Config.EditMasterPassword -> EditMaster(editMasterPassword(componentContext))
            is Config.PasswordList -> PasswordList(passwordList(componentContext))
            is Config.EditPassword -> EditPassword(
                editPassword(componentContext, config.passwordId)
            )
            is Config.CreatePassword -> CreatePassword(createPassword(componentContext))
            is Config.PasswordDetails -> PasswordDetails(
                passwordDetails(componentContext, config.passwordId, config.parentId)
            )
            is Config.PasswordGeneration -> Generation(passwordGeneration(componentContext))
            is Config.DevicesList -> DevicesList(devicesList(componentContext))
            is Config.Sync -> Sync(sync(componentContext, config.isClient))
            is Config.ResultSync -> ResultSync(resultSync(componentContext, config.isSyncSuccess))
        }

    private fun observeGeneratedPassword() = componentScope.launch {
        generatedPassword.collect {
            val password = it.generatedPassword ?: return@collect
            childStack.value.items
                .filterIsInstance<Child.Created<*, RootComponent.Child>>()
                .map { child -> child.instance }
                .sendGeneratedPasswordToChildren(password)

        }
    }

    private fun List<RootComponent.Child>.sendGeneratedPasswordToChildren(password: String) {
        this.forEach { child ->
            when (child) {
                is CreatePassword -> child.component.sendGeneratedPassword(password)
                is EditMaster -> child.component.sendGeneratedPassword(password)
                is EditPassword -> child.component.sendGeneratedPassword(password)
                else -> {}
            }
        }
    }

    private fun welcome(componentContext: ComponentContext) =
        WelcomeComponentImpl(
            componentContext = componentContext,
            onNavigateNext = {
                navigation.push(Config.EditMasterPassword)
            },
        )

    private fun login(componentContext: ComponentContext) =
        createLoginComponent(
            componentContext = componentContext,
            onSuccessfulLogin = {
                navigation.pop()
                navigation.push(Config.PasswordList)
            },
        )

    private fun editMasterPassword(componentContext: ComponentContext) =
        createEditMasterPasswordComponent(
            componentContext = componentContext,
            callbacks = createEditMasterPasswordCallbacks(),
        )

    private fun createEditMasterPasswordCallbacks() =
        EditMasterPasswordCallbacks(
            onSuccess = {
                navigation.pop()
                navigation.push(Config.PasswordList)
            },
            onCancel = { navigation.pop() },
            onNavigateToPasswordGenerationScreen = { navigation.push(Config.PasswordGeneration) },
        )

    private fun passwordList(componentContext: ComponentContext) =
        createPasswordListComponent(
            componentContext = componentContext,
            callbacks = createPasswordListComponentCallbacks(),
        )

    private fun createPasswordListComponentCallbacks() =
        PasswordListComponentCallbacks(
            navigateToPasswordDetailsScreen = { passwordId, parentId ->
                navigation.push(Config.PasswordDetails(passwordId, parentId))
            },
            navigateToSettingsScreen = { navigation.push(Config.DevicesList) },
            navigateToPasswordCreationScreen = { navigation.push(Config.CreatePassword) },
        )

    private fun editPassword(componentContext: ComponentContext, passwordId: String) =
        createEditPasswordComponent(
            componentContext = componentContext,
            passwordId = passwordId,
            callbacks = createEditPasswordComponentCallbacks(),
        )

    private fun createEditPasswordComponentCallbacks() =
        EditPasswordComponentCallbacks(
            onNavigateToPasswordGenerationScreen = { navigation.push(Config.PasswordGeneration) },
            onNavigateBack = { navigation.pop() },
        )

    private fun passwordDetails(
        componentContext: ComponentContext,
        passwordId: String,
        parentId: String
    ) =
        createPasswordDetailsComponent(
            componentContext = componentContext,
            passwordId = passwordId,
            parentId = parentId,
            callbacks = createPasswordDetailsComponentCallbacks(),
        )

    private fun createPasswordDetailsComponentCallbacks() =
        PasswordDetailsComponentCallbacks(
            navigateBack = { navigation.pop() },
            navigateToPasswordEditingScreen = {
                navigation.push(Config.EditPassword(passwordId = it))
            },
        )

    private fun createPassword(componentContext: ComponentContext) =
        createCreatePasswordComponent(
            componentContext = componentContext,
            callbacks = createCreatePasswordComponentCallbacks(),
        )

    private fun createCreatePasswordComponentCallbacks() =
        CreatePasswordComponentCallbacks(
            onNavigateToPasswordGenerationScreen = { navigation.push(Config.PasswordGeneration) },
            onNavigateBack = { navigation.pop() },
        )

    private fun passwordGeneration(componentContext: ComponentContext) =
        createPasswordGenerationComponent(
            componentContext = componentContext,
            onNavigateBack = { result ->
                navigation.pop()
                componentScope.launch {
                    generatedPassword.emit(result)
                }
            },
        )

    private fun devicesList(componentContext: ComponentContext) =
        createDevicesListComponent(
            componentContext = componentContext,
            callbacks = createDevicesListComponentCallbacks(),
        )

    private fun createDevicesListComponentCallbacks() =
        DevicesListComponentCallbacks(
            navigateBack = { navigation.pop() },
            navigateToSyncScreen = {
                navigation.push(Config.Sync(it))
            },
        )

    private fun sync(componentContext: ComponentContext, isClient: Boolean) =
        createSyncComponent(
            componentContext = componentContext,
            callbacks = createSyncComponentCallbacks(),
            isClient = isClient,
        )

    private fun createSyncComponentCallbacks() =
        SyncComponentCallbacks(
            navigateBack = { navigation.pop() },
            navigateToResultSyncScreen = {
                navigation.push(Config.ResultSync(it))
            },
        )

    private fun resultSync(componentContext: ComponentContext, isSyncSuccess: Boolean) =
        createResultSyncComponent(
            componentContext = componentContext,
            callbacks = createResultSyncComponentCallbacks(),
            isSyncSuccess = isSyncSuccess,
        )

    private fun createResultSyncComponentCallbacks() =
        ResultSyncComponentCallbacks(
            navigateToSettingsScreen = { /*navigation.replaceCurrent(Config.Settings)*/ },
            navigateToDevicesListScreen = { navigation.replaceCurrent(Config.DevicesList) },
            navigateToPasswordsListScreen = { navigation.replaceAll(Config.PasswordList) },
        )
}
