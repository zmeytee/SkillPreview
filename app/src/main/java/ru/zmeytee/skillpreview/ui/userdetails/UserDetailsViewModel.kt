package ru.zmeytee.skillpreview.ui.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.zmeytee.skillpreview.data.models.User
import ru.zmeytee.skillpreview.data.repositories.UserRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repository: UserRepositoryImpl
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)

    val currentUser = _currentUser.asStateFlow()

    fun getUser(id:Long) {
        viewModelScope.launch {
            _currentUser.value = repository.getUser(id)
        }
    }
}