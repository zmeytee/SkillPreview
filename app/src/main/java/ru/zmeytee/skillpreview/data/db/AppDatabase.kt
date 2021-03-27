package ru.zmeytee.skillpreview.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.zmeytee.skillpreview.data.db.AppDatabase.Companion.DB_VERSION
import ru.zmeytee.skillpreview.data.models.Address
import ru.zmeytee.skillpreview.data.models.Company
import ru.zmeytee.skillpreview.data.models.Geo
import ru.zmeytee.skillpreview.data.models.User

@Database(
    entities = [
        User.Local::class,
        Address.Local::class,
        Geo.Local::class,
        Company.Local::class
    ],
    version = DB_VERSION
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DB_NAME = "skill-preview-database"
        const val DB_VERSION = 2
    }
}