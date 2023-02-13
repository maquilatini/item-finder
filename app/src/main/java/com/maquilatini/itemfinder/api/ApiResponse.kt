package com.maquilatini.itemfinder.api

/**
 * A generic class that holds a Success, Error or NetworkError value.
 * @param <T>
 */
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val code: Int, val message: String) : ApiResponse<Nothing>()
    data class NetworkError(val message: String) : ApiResponse<Nothing>()
}