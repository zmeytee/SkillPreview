package ru.zmeytee.skillpreview.data.repositories

import kotlinx.coroutines.*
import ru.zmeytee.skillpreview.data.db.UserDao
import ru.zmeytee.skillpreview.data.models.Address
import ru.zmeytee.skillpreview.data.models.Company
import ru.zmeytee.skillpreview.data.models.Geo
import ru.zmeytee.skillpreview.data.models.User
import ru.zmeytee.skillpreview.data.networking.Api
import ru.zmeytee.skillpreview.data.repositories.interfaces.UserRepository
import ru.zmeytee.skillpreview.utils.Test
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: Api,
    private val userDao: UserDao,
    private val defaultDispatcher: CoroutineDispatcher
) : UserRepository {

    private var job: Job? = null

//    Test test test
    init {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            saveLocalUsers(Test.users)
            saveLocalAddress(Test.addresses)
            saveLocalGeo(Test.geo)
            saveLocalCompany(Test.companies)

            Timber.d(userDao.getAllUsers().joinToString("\n"))
        }
    }

    override suspend fun getAllUsers(): List<User> {
        return withContext(defaultDispatcher) {
            getAllRemoteUsers()
        }
    }

    override suspend fun getUser(id: Long): User {
        return withContext(defaultDispatcher) {
            getRemoteUser(id)
        }
    }

//===========================================================
//===================== Работа с сетью ======================
//===========================================================

    private suspend fun getAllRemoteUsers(): List<User.Remote> {
        return api.getAllUsers()
    }

    private suspend fun getRemoteUser(id: Long): User.Remote {
        return api.getUserById(id)
    }

//===========================================================
//======================= Работа с БД =======================
//===========================================================

    private suspend fun saveLocalUsers(users: List<User.Local>) {
        userDao.insertUsers(users)
    }

    private suspend fun saveLocalAddress(address: List<Address.Local>) {
        userDao.insertAddresses(address)
    }

    private suspend fun saveLocalGeo(geo: List<Geo.Local>) {
        userDao.insertGeo(geo)
    }

    private suspend fun saveLocalCompany(company: List<Company.Local>) {
        userDao.insertCompany(company)
    }

    private suspend fun deleteLocalUser(userId: Long) {
        userDao.deleteUserById(userId)
    }
}