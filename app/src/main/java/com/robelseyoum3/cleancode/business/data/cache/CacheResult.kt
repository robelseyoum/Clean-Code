package com.robelseyoum3.cleancode.business.data.cache

sealed class CacheResult <out T> {

    data class Success<out T>(val value: T): CacheResult<T>()

    data class GenericError(val errorMessages: String? = null): CacheResult<Nothing>()

}