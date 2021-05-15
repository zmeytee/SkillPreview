package ru.zmeytee.networkingsample.ui.userdetails

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
class UserDetailsViewModel @Inject constructor(
    private val repository: UserRepositoryImpl
) : ViewModel() {

    private var currentJob: Job? = null

    private val _isLoading = MutableStateFlow(false)
    private val _deletingSuccess = MutableStateFlow<Boolean?>(null)
    private val _currentUser = MutableStateFlow<User?>(null)

    val isLoading = _isLoading.asStateFlow()
    val deletingSuccess = _deletingSuccess.asStateFlow()
    val currentUser = _currentUser.asStateFlow()

    fun getUser(id:Long) {
        cancelJob()
        currentJob = viewModelScope.launch {
            kotlin.runCatching {
                _isLoading.value = true
                _currentUser.value = repository.getUser(id)
            }
                .onSuccess { _isLoading.value = false }
                .onFailure { _isLoading.value = false }
        }
    }

    fun deleteUser(id: Long) {
        cancelJob()
        currentJob = viewModelScope.launch {
            kotlin.runCatching {
                _isLoading.value = true
                repository.deleteUser(id)
            }
                .onSuccess {
                    _isLoading.value = false
                    _deletingSuccess.value = true
                }
                .onFailure {
                    _deletingSuccess.value = false
                    _isLoading.value = false
                }
        }
    }

    fun cancelJob() {
        currentJob?.cancelChildren()
    }

    fun resetStateFlow() {
        _deletingSuccess.value = null
    }

    override fun onCleared() {
        cancelJob()
        super.onCleared()
    }
}