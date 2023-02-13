package com.maquilatini.itemfinder.model.listing

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Listing

/**
 * Interface to represent a search result/listing model.
 */
interface IListingModel {
    suspend fun search(query: String? = "", categoryId: String? = "", offset: Int? = 0): ApiResponse<Listing>
}