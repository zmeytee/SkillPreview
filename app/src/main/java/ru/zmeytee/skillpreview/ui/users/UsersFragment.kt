package ru.zmeytee.skillpreview.ui.users

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.zmeytee.skillpreview.R
import ru.zmeytee.skillpreview.data.repositories.UserRepository
import ru.zmeytee.skillpreview.factories.UsersViewModelFactory
import ru.zmeytee.skillpreview.networking.Networking

class UsersFragment: Fragment(R.layout.fragment_users) {

    //Manual DI
    private val api = Networking.api
    private val repository = UserRepository(api)
    private val viewModelFactory = UsersViewModelFactory(repository)
    private val viewModel: UsersViewModel by viewModels { viewModelFactory }
}