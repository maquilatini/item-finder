package com.maquilatini.itemfinder.model.categories

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Category

/**
 * Interface to represent a categories model.
 */
interface ICategoriesModel {
    suspend fun getCategories(siteId: String): ApiResponse<List<Category>>
}