package ru.zmeytee.skillpresentation.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.zmeytee.skillpresentation.data.models.User
import ru.zmeytee.skillpresentation.data.repositories.UserRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: UserRepositoryImpl): ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())

    val users = _users.asStateFlow()

    fun getListOfAllUsers() {
        viewModelScope.launch {
            _users.value = repository.getAllUsers()
        }
    }
}