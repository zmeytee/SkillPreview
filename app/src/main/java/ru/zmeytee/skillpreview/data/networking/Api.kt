package ru.zmeytee.skillpreview.data.networking

import retrofit2.http.GET
import retrofit2.http.Path
import ru.zmeytee.skillpreview.data.models.User

interface Api {

    @GET("/users")
    suspend fun getAllUsers(): List<User.Remote>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Long
    ): User.Remote
}