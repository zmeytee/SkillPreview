package ru.zmeytee.skillpreview.data.models

import androidx.room.*
import com.squareup.moshi.JsonClass
import ru.zmeytee.skillpreview.data.contracts.DbContracts

sealed class Address {

    @JsonClass(generateAdapter = true)
    data class Remote(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: Geo.Remote
    )

    @Entity(
        tableName = DbContracts.Address.TABLE_NAME,
        foreignKeys = [
            ForeignKey(
                entity = User.Local::class,
                parentColumns = [DbContracts.User.ID],
                childColumns = [DbContracts.Address.USER_ID],
                onDelete = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(DbContracts.Address.USER_ID)
        ]
    )
    data class Local(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbContracts.Address.ID)
        val id: Long,

        @ColumnInfo(name = DbContracts.Address.USER_ID)
        val userId: Long,

        @ColumnInfo(name = DbContracts.Address.STREET)
        val street: String,

        @ColumnInfo(name = DbContracts.Address.SUITE)
        val suite: String,

        @ColumnInfo(name = DbContracts.Address.CITY)
        val city: String,

        @ColumnInfo(name = DbContracts.Address.ZIP_CODE)
        val zipcode: String
    )
}
