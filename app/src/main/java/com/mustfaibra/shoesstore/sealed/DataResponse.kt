package com.mustfaibra.shoesstore.sealed

sealed class DataResponse<T>(
    var data: T? = null,
    var error: com.mustfaibra.shoesstore.sealed.Error? = null,
) {
    class Success<T>(data: T) : DataResponse<T>(data = data)
    class Error<T>(error: com.mustfaibra.shoesstore.sealed.Error) : DataResponse<T>(error = error)
}