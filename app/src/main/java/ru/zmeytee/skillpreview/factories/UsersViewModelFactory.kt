package ru.zmeytee.skillpreview.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zmeytee.skillpreview.data.repositories.UserRepository
import ru.zmeytee.skillpreview.ui.users.UsersViewModel

class UsersViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            UsersViewModel::class.java -> UsersViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}