package com.robelseyoum3.cleancode.business.data.cache

object CacheErrors {
    const val CACHE_ERROR_UNKNOWN = "Unknown cache error"
    const val CACHE_ERROR = "Cache error"
    const val CACHE_ERROR_TIMEOUT = "Cache timeout"
    const val CACHE_DATA_NULL  = "Cache data is null"
}

/*
sealed class CacheResult <out T> {

    data class Success<out T>(val value: T): CacheResult<T>()

    data class GenericError(val errorMessages: String? = null): CacheResult<Nothing>()

}
 */

/*

object CacheConstants {

    const val CACHE_TIMEOUT = 2000L

}

 */

/*
data class Response(
    val message: String?,
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)
 */