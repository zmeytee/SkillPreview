package ru.zmeytee.skillpreview.data.models

import androidx.room.*
import com.squareup.moshi.JsonClass
import ru.zmeytee.skillpreview.data.contracts.DbContracts

sealed class Geo {

    @JsonClass(generateAdapter = true)
    data class Remote(
        val lat: Double,
        val lng: Double
    )

    @Entity(
        tableName = DbContracts.Geo.TABLE_NAME,
        foreignKeys = [
            ForeignKey(
                entity = Address.Local::class,
                parentColumns = [DbContracts.Address.ID],
                childColumns = [DbContracts.Geo.ADDRESS_ID],
                onDelete = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(DbContracts.Geo.ADDRESS_ID)
        ]
    )
    data class Local(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbContracts.Geo.ID)
        val id: Long,

        @ColumnInfo(name = DbContracts.Geo.ADDRESS_ID)
        val addressId: Long,

        @ColumnInfo(name = DbContracts.Geo.LATITUDE)
        val lat: Double,

        @ColumnInfo(name = DbContracts.Geo.LONGITUDE)
        val lng: Double
    )
}
