package com.maquilatini.itemfinder.api.service

import com.maquilatini.itemfinder.api.ApiResponse.NetworkError

open class BaseService {

    fun getApiError(exception: Exception): NetworkError {
        val message = exception.message ?: ""
        return NetworkError(message)
    }
}