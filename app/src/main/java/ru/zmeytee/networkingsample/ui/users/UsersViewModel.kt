package ru.zmeytee.networkingsample.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.zmeytee.networkingsample.data.models.User
import ru.zmeytee.networkingsample.data.repositories.UserRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UserRepositoryImpl
) : ViewModel() {

    private var currentJob: Job? = null

    private val _isLoading = MutableStateFlow(false)
    private val _users = MutableStateFlow<List<User>>(emptyList())

    val isLoading = _isLoading.asStateFlow()
    val users = _users.asStateFlow()

    fun getListOfAllUsers() {
        cancelJob()
        currentJob = viewModelScope.launch {
            kotlin.runCatching {
                _isLoading.value = true
                val list = repository.getAllUsers()
                _users.value = list
            }
                .onSuccess { _isLoading.value = false }
                .onFailure { _isLoading.value = false }
        }
    }

    fun cancelJob() {
        currentJob?.cancelChildren()
    }

    override fun onCleared() {
        cancelJob()
        super.onCleared()
    }
}