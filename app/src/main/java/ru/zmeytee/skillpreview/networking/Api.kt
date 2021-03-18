package ru.zmeytee.skillpreview.networking

import retrofit2.http.GET
import ru.zmeytee.skillpreview.data.models.User

interface Api {

    @GET("/users")
    suspend fun getAllUsers(): List<User>
}