package com.maquilatini.itemfinder.api.service.categories

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.maquilatini.itemfinder.api.model.Category

/**
 * Class to implement categories service.
 */
class CategoriesService : ICategoriesService {

    private val categoriesApi: CategoriesApi by lazy {
        RetrofitFactory.getServiceRetrofit(CategoriesApi::class.java)
    }

    override suspend fun getCategories(siteId: String): ApiResponse<List<Category>> {
        return withContext(Dispatchers.IO) {
            val response = categoriesApi.getCategories(siteId)
            if (response.isSuccessful) {
                ApiResponse.Success(response.body()?.toList() ?: emptyList())
            } else {
                ApiResponse.Error(response.code(), response.message())
            }
        }
    }
}