package ru.zmeytee.skillpreview.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.zmeytee.skillpreview.data.contracts.DbContracts

sealed class User {

    @JsonClass(generateAdapter = true)
    data class Remote(
        val id: Long,
        val name: String,
        @Json(name = "username") val userName: String,
        val email: String,
        val address: Address.Remote,
        val phone: String,
        val website: String,
        val company: Company.Remote
    ): User()

    @Entity(tableName = DbContracts.User.TABLE_NAME)
    data class Local(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbContracts.User.ID)
        val id: Long,

        @ColumnInfo(name = DbContracts.User.NAME)
        val name: String,

        @ColumnInfo(name = DbContracts.User.USER_NAME)
        val userName: String,

        @ColumnInfo(name = DbContracts.User.EMAIL)
        val email: String,

        @ColumnInfo(name = DbContracts.User.PHONE)
        val phone: String,

        @ColumnInfo(name = DbContracts.User.WEBSITE)
        val website: String,
    )
}