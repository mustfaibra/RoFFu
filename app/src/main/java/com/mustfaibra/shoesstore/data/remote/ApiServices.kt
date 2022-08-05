package com.mustfaibra.shoesstore.data.remote

import com.mustfaibra.shoesstore.models.User
import com.mustfaibra.shoesstore.sealed.DataResponse

interface ApiServices {

    /** A function to authorize an existed user */
    suspend fun signIn(
        email: String,
        password: String,
    ) : DataResponse<User>

    /** A function to create a new user */
    suspend fun signup(
        email: String,
        password: String,
        name: String,
    ) : DataResponse<User>
}