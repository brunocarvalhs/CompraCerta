package br.com.brunocarvalhs.compracerta.features.home.app.presentation

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.com.brunocarvalhs.compracerta.commons.database.AppDatabase
import br.com.brunocarvalhs.compracerta.features.home.app.data.repositories.GroupRepositoryImpl
import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group
import br.com.brunocarvalhs.compracerta.features.home.app.domain.repositories.GroupRepository
import br.com.brunocarvalhs.compracerta.features.home.app.domain.useCases.CreateGroupUseCase
import br.com.brunocarvalhs.compracerta.features.home.app.domain.useCases.DeleteGroupUseCase
import br.com.brunocarvalhs.compracerta.features.home.app.domain.useCases.GetGroupListUseCase
import br.com.brunocarvalhs.compracerta.features.home.app.domain.useCases.SharedGroupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getGroupListUseCase: GetGroupListUseCase,
    private val createGroupUseCase: CreateGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val sharedGroupUseCase: SharedGroupUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FetchData -> fetchGroupList()
            is HomeIntent.CreateGroup -> createGroup(callback = intent.callback)
            is HomeIntent.DeleteGroup -> deleteGroup(group = intent.group)
            is HomeIntent.ShareGroup -> shareGroup(context = intent.context, group = intent.group)
        }
    }

    private fun fetchGroupList() {
        viewModelScope.launch {
            getGroupListUseCase.invoke()
                .onSuccess { group ->
                    _uiState.value = HomeUiState.Success(group)
                }.onFailure {
                    _uiState.value = HomeUiState.Error(it.message.orEmpty())
                }
        }
    }

    private fun createGroup(groupName: String? = null, callback: (Group) -> Unit = {}) {
        viewModelScope.launch {
            val name = groupName?.ifBlank { "Grupo" }
            createGroupUseCase.invoke(groupName = name.orEmpty())
                .onSuccess { group ->
                    callback(group)
                }.onFailure {
                    _uiState.value = HomeUiState.Error(it.message.orEmpty())
                }
        }
    }

    private fun deleteGroup(group: Group) {
        viewModelScope.launch {
            deleteGroupUseCase.invoke(groupId = group.id)
                .onSuccess { _ ->
                    fetchGroupList()
                }.onFailure {
                    _uiState.value = HomeUiState.Error(it.message.orEmpty())
                }
        }
    }

    private fun shareGroup(group: Group, context: Context) {
        viewModelScope.launch {
            sharedGroupUseCase.invoke(groupId = group.id)
                .onSuccess { message ->

                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, message)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, group.name)
                    context.startActivity(shareIntent)

                }.onFailure {
                    _uiState.value = HomeUiState.Error(it.message.orEmpty())
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) {
                    "Application is required to initialize ViewModel"
                }
                val database = AppDatabase.getInstance(context = application.applicationContext)
                val repository: GroupRepository = GroupRepositoryImpl(database.groupDao())

                val getGroupListUseCase = GetGroupListUseCase(repository = repository)
                val createGroupUseCase = CreateGroupUseCase(repository = repository)
                val deleteGroupUseCase = DeleteGroupUseCase(repository = repository)
                val sharedGroupUseCase = SharedGroupUseCase(repository = repository)

                HomeViewModel(
                    getGroupListUseCase = getGroupListUseCase,
                    createGroupUseCase = createGroupUseCase,
                    deleteGroupUseCase = deleteGroupUseCase,
                    sharedGroupUseCase = sharedGroupUseCase,
                )
            }
        }
    }
}