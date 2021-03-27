package ru.zmeytee.skillpreview.data.repositories.interfaces

import ru.zmeytee.skillpreview.data.models.User

interface UserRepository {

    suspend fun getAllUsers(): List<User>
    suspend fun getUser(id: Long): User
}