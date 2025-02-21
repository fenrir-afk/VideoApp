package com.example.videoapp.core.data.networking


import com.example.videoapp.core.domain.util.Result
import com.example.videoapp.core.domain.util.NetworkError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse


suspend inline fun <reified T> responseToResult(
    response:HttpResponse
):Result<T, NetworkError>{
    return when(response.status.value){
        in 200..299 ->{
            try {
                Result.Success(response.body<T>())
            }catch (e: NoTransformationFoundException){
                Result.Error(NetworkError.SERIALIZATION)
            }
        }
        400 -> Result.Error(NetworkError.INVALID_REQUEST_PARAMS)
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.RATE_LIMIT_EXCEEDED)
        404 -> Result.Error(NetworkError.RESOURCE_NOT_FOUND)
        in 500 .. 599 -> Result.Error(NetworkError.INTERNAL_SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}