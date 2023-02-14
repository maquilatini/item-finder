package com.maquilatini.itemfinder.viewmodel

sealed class ViewModelResponse<out T>
data class Loading<out T>(val isLoading: Boolean) : ViewModelResponse<T>()
data class SuccessResponse<out T>(val data: T) : ViewModelResponse<T>()
data class ErrorResponse<out T>(val code: Int, val message: String) : ViewModelResponse<T>()
data class DeviceOffline<out T>(val message: String) : ViewModelResponse<T>()