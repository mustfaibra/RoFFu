package com.mustfaibra.shoesstore.data.remote

import com.mustfaibra.shoesstore.R
import com.mustfaibra.shoesstore.models.User
import com.mustfaibra.shoesstore.sealed.DataResponse
import io.ktor.client.*
import javax.inject.Inject

class ApiServicesImpl @Inject constructor(
    private val client: HttpClient,
) : ApiServices {
    /** A function to authorize an existed user */
    override suspend fun signIn(email: String, password: String): DataResponse<User> {
//        return try {
//            val response: HttpResponse = client.post(urlString = Url.SIGNIN_URL) {
//                //body =
//                contentType(type = ContentType.Application.Json)
//            }
//            /** Return our user response */
//            DataResponse.Success(data = response.receive())
//
//        } catch (t: Throwable) {
//            /** Handle the case of client throwing a code other than 2xx */
//            t.handleResponseException()
//        }
        return DataResponse.Success(
            data = User(
                userId = 1,
                name = "Mustafa Ibrahim",
                profile = R.drawable.mustapha_profile,
                phone = "+249922943879",
                email = email,
                token = "4sjskjslk3kjld98sfhhhs9ss"
            )
        )
    }

    /** A function to create a new user */
    override suspend fun signup(email: String, password: String, name: String): DataResponse<User> {
//        return try {
//            val response: HttpResponse = client.post(urlString = Url.SIGNUP_URL) {
//                //body =
//                contentType(type = ContentType.Application.Json)
//            }
//            /** Return our user response */
//            DataResponse.Success(data = response.receive())
//        } catch (t: Throwable) {
//            /** Handle the case of client throwing a code other than 2xx */
//            t.handleResponseException()
//        }
        return DataResponse.Success(
            data = User(
                userId = 1,
                name = name,
                profile = R.drawable.ic_profile_empty,
                phone = "Not added yet",
                email = email,
                token = "4sjskjslk3kjld98sfhhhs9ss"
            )
        )
    }
}