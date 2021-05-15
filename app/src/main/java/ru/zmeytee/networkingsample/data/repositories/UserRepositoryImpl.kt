package ru.zmeytee.networkingsample.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.zmeytee.networkingsample.data.models.User
import ru.zmeytee.networkingsample.data.repositories.interfaces.UserRepository
import ru.zmeytee.networkingsample.networking.Api
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: Api,
    private val defaultDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getAllUsers(): List<User> {
        return withContext(defaultDispatcher) {
            api.getAllUsers()
        }
    }

    override suspend fun getUser(id: Long): User {
        return withContext(defaultDispatcher) {
            api.getUserById(id)
        }
    }

    override suspend fun saveUser(user: User): User {
        return withContext(defaultDispatcher) {
            api.saveUser(user)
        }
    }

    override suspend fun deleteUser(id: Long) {
        withContext(defaultDispatcher) {
            api.deleteUserById(id)
        }
    }
}