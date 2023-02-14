package com.maquilatini.itemfinder.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Category
import com.maquilatini.itemfinder.model.categories.ICategoriesModel
import com.maquilatini.itemfinder.viewmodel.DeviceOffline
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import com.maquilatini.itemfinder.viewmodel.ViewModelResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val model: ICategoriesModel) : ViewModel(), IHomeViewModel {

    private var categories: List<Category> = emptyList()
    private val _categoriesLiveData: MutableLiveData<ViewModelResponse<List<Category>>> = MutableLiveData()
    override val categoriesLiveData: LiveData<ViewModelResponse<List<Category>>> get() = _categoriesLiveData

    override fun getCategories(siteId: String) {
        viewModelScope.launch {
            _categoriesLiveData.value = Loading(true)
            when (val result = model.getCategories(siteId)) {
                is ApiResponse.Success -> {
                    _categoriesLiveData.value = SuccessResponse(result.data)
                    categories = result.data
                }
                is ApiResponse.Error -> {
                    _categoriesLiveData.value = ErrorResponse(result.code, result.message)
                    categories = emptyList()
                }
                is ApiResponse.NetworkError -> {
                    _categoriesLiveData.value = DeviceOffline(message = result.message)
                    categories = emptyList()
                }
            }
        }
    }

    fun getLoadedCategories(): List<Category> {
        return categories
    }
}

