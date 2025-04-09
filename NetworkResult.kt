package com.example.productcatalog.utils

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val code: Int? = null) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccessful(): Boolean = this is Success
    fun isError(): Boolean = this is Error

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun getErrorMessage(): String? = when (this) {
        is Error -> message
        else -> null
    }
}