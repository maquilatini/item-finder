package com.maquilatini.itemfinder.api.service.categories

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import com.maquilatini.itemfinder.api.model.Category

/**
 * Interface to categories API.
 */
interface CategoriesApi {
    @GET("/sites/{siteId}/categories")
    suspend fun getCategories(@Path("siteId") siteId: String): Response<List<Category>>
}