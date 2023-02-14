package com.maquilatini.itemfinder.viewmodel.home

import androidx.lifecycle.LiveData
import com.maquilatini.itemfinder.api.model.Category
import com.maquilatini.itemfinder.viewmodel.ViewModelResponse

/**
 * Categories View Model Interface.
 */
interface IHomeViewModel {
    val categoriesLiveData: LiveData<ViewModelResponse<List<Category>>>
    fun getCategories(siteId: String)
}