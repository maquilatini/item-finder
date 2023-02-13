package com.maquilatini.itemfinder.api.service

import com.maquilatini.itemfinder.api.ApiResponse
import retrofit2.Response

/**
 * This extension function evaluates the status response an returns an ApiResponse
 */
fun <Data> Response<Data>.parse(): ApiResponse<Data> {
    return if (this.isSuccessful) {
        this.body()?.let {
            ApiResponse.Success(it)
        } ?: ApiResponse.Error(this.code(), this.message())
    } else {
        ApiResponse.Error(this.code(), this.message())
    }
}