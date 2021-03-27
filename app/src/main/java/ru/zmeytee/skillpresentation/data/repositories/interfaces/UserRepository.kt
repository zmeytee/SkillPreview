package ru.zmeytee.skillpresentation.data.repositories.interfaces

import ru.zmeytee.skillpresentation.data.models.User

interface UserRepository {

    suspend fun getAllUsers(): List<User>
    suspend fun getUser(id: Long): User
}