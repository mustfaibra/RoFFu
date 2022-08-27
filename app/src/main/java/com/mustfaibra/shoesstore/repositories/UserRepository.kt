package com.mustfaibra.shoesstore.repositories

import com.mustfaibra.shoesstore.data.local.RoomDao
import com.mustfaibra.shoesstore.data.remote.ApiServicesImpl
import com.mustfaibra.shoesstore.models.User
import com.mustfaibra.shoesstore.sealed.DataResponse
import com.mustfaibra.shoesstore.sealed.Error
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiServicesImpl: ApiServicesImpl,
    private val dao: RoomDao,
) {
    suspend fun signInUser(email: String, password: String): DataResponse<User> {
        return dao.fakeSignIn(email = email, password = password)?.let {
            DataResponse.Success(data = it)
        } ?: DataResponse.Error(error = Error.Empty)
//        return apiServicesImpl.signIn(
//            email = email,
//            password = password
//        )
    }

    suspend fun signUpUser(email: String, password: String, name: String) =
        apiServicesImpl.signup(
            email = email,
            password = password,
            name = name,
        )

    suspend fun getLoggedUser(userId: Int) = dao.getLoggedUser(userId = userId)

    suspend fun getUserPaymentProviders() =
        dao.getUserPaymentProviders()

    suspend fun getUserLocations() = dao.getUserLocations()

    suspend fun saveUserLocally(user: User) {
        dao.saveUser(user = user)
    }
}