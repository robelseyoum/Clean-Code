package com.robelseyoum3.cleancode.business.data.util

import com.robelseyoum3.cleancode.business.data.cache.CacheConstants.CACHE_TIMEOUT
import com.robelseyoum3.cleancode.business.data.cache.CacheErrors.CACHE_ERROR_TIMEOUT
import com.robelseyoum3.cleancode.business.data.cache.CacheErrors.CACHE_ERROR_UNKNOWN
import com.robelseyoum3.cleancode.business.data.cache.CacheResult
import com.robelseyoum3.cleancode.business.data.network.ApiResult
import com.robelseyoum3.cleancode.business.data.network.NetworkConstants.NETWORK_TIMEOUT
import com.robelseyoum3.cleancode.business.data.network.NetworkErrors.NETWORK_ERROR_TIMEOUT
import com.robelseyoum3.cleancode.business.data.network.NetworkErrors.NETWORK_ERROR_UNKNOWN
import com.robelseyoum3.cleancode.business.data.util.GenericErrors.ERROR_UNKNOWN
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */

suspend fun <T> safeApi(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T? //It takes a function as input and in order to execute the function you have to call cacheCall.invoke() on it
): ApiResult<T?> {

    return withContext(dispatcher) {
        try {
//            throws TimeoutCancellationException
            withTimeout(NETWORK_TIMEOUT){
                ApiResult.Success(apiCall.invoke()) //if it is success then it will pass the return type of the suspend function T (type of T) this -(apiCall: suspend () -> T? )
            }

        } catch (throwable: Throwable){

            throwable.printStackTrace()

            when(throwable) {

                is TimeoutCancellationException -> {
                    val code = 408 //timeout error code
                    ApiResult.GenericError(code, NETWORK_ERROR_TIMEOUT)
                }

                is IOException -> {
                    ApiResult.NetworkError
                }

                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ApiResult.GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    ApiResult.GenericError(
                        null,
                        NETWORK_ERROR_UNKNOWN
                    )
                }
            }
        }
    }
}

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T? //It takes a function as input and in order to execute the function you have to call cacheCall.invoke() on it
): CacheResult<T?>  {

    return withContext(dispatcher) {

        try {

            // throws TimeoutCancellationException
            withTimeout(CACHE_TIMEOUT){
                CacheResult.Success(cacheCall.invoke()) //if it is success then it will pass the return type of the suspend function T (type of T) this - cacheCall: suspend () -> T?
            }

        } catch (throwable: Throwable) {

            throwable.printStackTrace()

            when(throwable){

                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }

                else -> {
                    CacheResult.GenericError(CACHE_ERROR_UNKNOWN)
                }
            }
        }
    }
}



fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception){
        ERROR_UNKNOWN
    }
}
