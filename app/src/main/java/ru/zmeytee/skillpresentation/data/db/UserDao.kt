package ru.zmeytee.skillpresentation.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.zmeytee.skillpresentation.data.contracts.DbContracts
import ru.zmeytee.skillpresentation.data.models.Address
import ru.zmeytee.skillpresentation.data.models.Company
import ru.zmeytee.skillpresentation.data.models.Geo
import ru.zmeytee.skillpresentation.data.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM ${DbContracts.User.TABLE_NAME}")
    suspend fun getAllUsers(): List<User.Local>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User.Local>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddresses(address: List<Address.Local>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeo(geo: List<Geo.Local>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(companies: List<Company.Local>)

    @Query("DELETE FROM ${DbContracts.User.TABLE_NAME} WHERE ${DbContracts.User.ID} = :userId")
    suspend fun deleteUserById(userId: Long)
}