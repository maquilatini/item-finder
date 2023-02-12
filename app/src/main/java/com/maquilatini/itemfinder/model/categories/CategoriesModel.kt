package com.maquilatini.itemfinder.model.categories

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Category
import com.maquilatini.itemfinder.api.service.categories.ICategoriesService

class CategoriesModel(private val service: ICategoriesService) :
    ICategoriesModel {

    override suspend fun getCategories(siteId: String): ApiResponse<List<Category>> {
        return service.getCategories(siteId)
    }
}