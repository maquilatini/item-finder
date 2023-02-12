package com.maquilatini.itemfinder.api

/**
 * A generic class that holds a Success value or Error.
 * @param <T>
 */
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val code: Int, val message: String) : ApiResponse<Nothing>()
}