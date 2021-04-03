package ru.zmeytee.networkingsample.ui.useradd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.zmeytee.networkingsample.data.models.User
import ru.zmeytee.networkingsample.data.repositories.interfaces.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserAddViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private var currentJob: Job? = null

    private val _isLoading = MutableStateFlow(false)
    private val _addingSuccess = MutableStateFlow<Boolean?>(null)

    val isLoading = _isLoading.asStateFlow()
    val addingSuccess = _addingSuccess.asStateFlow()

    fun saveUser(user: User) {
        cancelJob()
        currentJob = viewModelScope.launch {
            kotlin.runCatching {
                _isLoading.value = true
                repository.saveUser(user)
            }
                .onSuccess {
                    _isLoading.value = false
                    _addingSuccess.value = true
                }
                .onFailure {
                    _addingSuccess.value = false
                    _isLoading.value = false
                }
        }
    }

    fun resetStateFlow() {
        _addingSuccess.value = null
    }

    fun cancelJob() {
        currentJob = null
    }

    override fun onCleared() {
        cancelJob()
        super.onCleared()
    }
}