package com.example.videoapp.core.data.networking

import com.example.videoapp.core.domain.util.NetworkError
import com.example.videoapp.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
):Result<T, NetworkError>{
    val response = try {
        execute()
    }catch (e:UnresolvedAddressException){
        e.printStackTrace()
        return Result.Error(NetworkError.NO_INTERNET)
    }catch (e:SerializationException){
        e.printStackTrace()
        return Result.Error(NetworkError.SERIALIZATION)
    }
    catch (e:Exception){
        coroutineContext.ensureActive()
        e.printStackTrace()
        return Result.Error(NetworkError.UNKNOWN)
    }
    return responseToResult(response)
}