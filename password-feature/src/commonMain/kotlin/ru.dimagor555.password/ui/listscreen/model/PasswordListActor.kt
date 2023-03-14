package ru.dimagor555.password.ui.listscreen.model

import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.domain.filter.FavouriteFilter
import ru.dimagor555.password.domain.filter.SortingType
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.domain.password.field.ShortTextField
import ru.dimagor555.password.domain.password.field.TITLE_FIELD_KEY
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.*
import ru.dimagor555.password.usecase.folder.CreateFolderUseCase
import ru.dimagor555.res.core.MR

internal class PasswordListActor : Actor<State, Action, Message, SideEffect>(), KoinComponent {

    private val useCases: PasswordListUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            Action.InitScreen -> initScreen()
            is Action.ChangeSearchText -> changeSearchText(action.searchText)
            Action.ClearSearchText -> clearSearchText()
            is Action.ChangeFavouriteFilter -> changeFavouriteFilter(action.favouriteFilter)
            is Action.ChangeSortingDialogVisibility -> sendMessage(
                Message.ChangeSortingDialogVisibility(action.isVisible)
            )
            is Action.ChangeSortingType -> changeSortingType(action.sortingType)
            is Action.CopyPassword -> copyPassword(action.passwordId)
            is Action.ToggleFavourite -> toggleFavourite(action.passwordId)
        }
    }

    private suspend fun initScreen() {
        val rootFolder = useCases.getFolderUseCase(Folder.ROOT_FOLDER_ID)
        val archiveFolder = useCases.getFolderUseCase(Folder.ARCHIVE_FOLDER_ID)
        if (rootFolder == null && archiveFolder == null) {
            createMainFolders()
        }
        observeAllPasswords()
    }

    private suspend fun observeAllPasswords() = coroutineScope {
        launch { observePasswords() }
        launch { observePasswordFilter() }
    }

    private suspend fun createMainFolders() {
        useCases.createFolderUseCase(
            CreateFolderUseCase.Params(
                id = Folder.ROOT_FOLDER_ID,
                parentId = "",
                title = ShortTextField(TITLE_FIELD_KEY, "Root"),
                children = emptySet(),
            )
        )
        useCases.createFolderUseCase(
            CreateFolderUseCase.Params(
                id = Folder.ARCHIVE_FOLDER_ID,
                parentId = "",
                title = ShortTextField(TITLE_FIELD_KEY, "Archive"),
                children = emptySet(),
            )
        )
    }

    private suspend fun observePasswords() {
        useCases.observeFolderChildrenUseCase(Folder.ROOT_FOLDER_ID)
            .collect {
                if (it != null) {
                    onNewPassword(it)
                }
            }
    }

    private suspend fun onNewPassword(folderChildren: FolderChildren) {
        val passwordIds = folderChildren.childrenIds.filterIsInstance<ChildId.PasswordId>()
        val ids = passwordIds.map { it.id }.toSet()
        val passwordStates = useCases.getPasswordsByIdsUseCase(ids).toPasswordStates()
        sendMessage(Message.ShowPasswordStates(passwordStates))
        sendMessage(Message.ShowLoading(isLoading = false))
    }

    private suspend fun observePasswordFilter() {
        useCases.observePasswordFilterState()
            .collect { sendMessage(Message.ShowFilterState(it)) }
    }

    private suspend fun changeSearchText(searchText: String) {
        useCases.updatePasswordFilterState(
            getState().filterState.copy(searchText = searchText)
        )
    }

    private suspend fun clearSearchText() {
        changeSearchText("")
        sendMessage(Message.ShowLoading(isLoading = true))
    }

    private suspend fun changeFavouriteFilter(favouriteFilter: FavouriteFilter) {
        useCases.updatePasswordFilterState(
            getState().filterState.copy(favouriteFilter = favouriteFilter)
        )
        sendMessage(Message.ShowLoading(isLoading = true))
    }

    private suspend fun changeSortingType(sortingType: SortingType) {
        useCases.updatePasswordFilterState(
            getState().filterState.copy(sortingType = sortingType)
        )
    }

    private suspend fun copyPassword(passwordId: String) {
        useCases.copyPassword(passwordId)
        showPasswordCopiedMessage()
    }

    private suspend fun showPasswordCopiedMessage() {
        val message = MR.strings.password_copied.desc()
        sendSideEffect(SideEffect.ShowMessage(message))
    }

    private suspend fun toggleFavourite(passwordId: String) {
        useCases.toggleFavourite(passwordId)
    }
}
