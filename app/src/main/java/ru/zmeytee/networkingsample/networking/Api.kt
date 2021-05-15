package ru.zmeytee.networkingsample.networking

import retrofit2.http.*
import ru.zmeytee.networkingsample.data.models.User

interface Api {

    @GET("users")
    suspend fun getAllUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Long
    ): User

    @POST("users")
    suspend fun saveUser(
        @Body user: User
    ): User

    @DELETE("users/{id}")
    suspend fun deleteUserById(
        @Path("id") id: Long
    )
}