package com.maquilatini.itemfinder.api.service.categories

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.maquilatini.itemfinder.api.model.Category
import com.maquilatini.itemfinder.api.service.BaseService
import com.maquilatini.itemfinder.api.service.parse

/**
 * Class to implement categories service.
 */
class CategoriesService : BaseService(), ICategoriesService {

    private val categoriesApi: CategoriesApi by lazy {
        RetrofitFactory.getServiceRetrofit(CategoriesApi::class.java)
    }

    override suspend fun getCategories(siteId: String): ApiResponse<List<Category>> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext categoriesApi.getCategories(siteId).parse()
            } catch (e: Exception) {
                return@withContext getApiError(e)
            }
        }
    }
}