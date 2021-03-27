package ru.zmeytee.skillpresentation.data.models

import androidx.room.*
import com.squareup.moshi.JsonClass
import ru.zmeytee.skillpresentation.data.contracts.DbContracts

sealed class Company {

    @JsonClass(generateAdapter = true)
    data class Remote(
        val name: String,
        val catchPhrase: String,
        val bs: String
    )

    @Entity(
        tableName = DbContracts.Company.TABLE_NAME,
        foreignKeys = [
            ForeignKey(
                entity = User.Local::class,
                parentColumns = [DbContracts.User.ID],
                childColumns = [DbContracts.Company.USER_ID],
                onDelete = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(DbContracts.Company.USER_ID)
        ]
    )
    data class Local(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbContracts.Company.ID)
        val id: Long,

        @ColumnInfo(name = DbContracts.Company.USER_ID)
        val userId: Long,

        @ColumnInfo(name = DbContracts.Company.NAME)
        val name: String,

        @ColumnInfo(name = DbContracts.Company.CATCH_PHRASE)
        val catchPhrase: String,

        @ColumnInfo(name = DbContracts.Company.BS)
        val bs: String
    )
}
