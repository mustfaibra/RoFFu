package com.mustfaibra.shoesstore.repositories

import com.mustfaibra.shoesstore.data.local.RoomDao
import com.mustfaibra.shoesstore.data.remote.ApiServicesImpl
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiServicesImpl: ApiServicesImpl,
    private val dao: RoomDao,
) {
    suspend fun signInUser(email: String, password: String) =
        apiServicesImpl.signIn(
            email = email,
            password = password
        )

    suspend fun signUpUser(email: String, password: String, name: String) =
        apiServicesImpl.signup(
            email = email,
            password = password,
            name = name,
        )

    suspend fun getLoggedUser() = dao.getLoggedUser()
}