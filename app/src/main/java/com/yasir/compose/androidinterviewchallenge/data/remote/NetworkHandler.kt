package com.yasir.compose.androidinterviewchallenge.data.remote

import com.yasir.compose.androidinterviewchallenge.data.repository.ErrorType
import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import com.yasir.compose.androidinterviewchallenge.data.repository.Result.Success
import com.yasir.compose.androidinterviewchallenge.data.repository.Result.Error
import retrofit2.HttpException
import java.io.IOException

class NetworkHandler {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Success(apiCall.invoke())
        } catch (e: Exception) {
            Error(mapError(e))
        }
    }

    private fun mapError(e: Exception): ErrorType {
        return when (e) {
            is IOException -> ErrorType.Network
            is HttpException -> ErrorType.Http(e.code(), e.message())
            else -> ErrorType.Unknown
        }
    }
}