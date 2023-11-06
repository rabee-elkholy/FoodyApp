package com.androdu.foody.utils

sealed class ApiResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Error<T>(data: T? = null, message: String?) : ApiResult<T>(data, message)
    class Loading<T> : ApiResult<T>()
}