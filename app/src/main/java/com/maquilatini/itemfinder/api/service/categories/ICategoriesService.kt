package com.maquilatini.itemfinder.api.service.categories

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Category

/**
 * Interface to categories service.
 */
interface ICategoriesService {
    suspend fun getCategories(siteId: String): ApiResponse<List<Category>>
}