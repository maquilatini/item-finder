package com.maquilatini.itemfinder.api.service.listing

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Listing

/**
 * Interface to search result/listing service.
 */
interface IListingService {
    suspend fun search(query: String?, category: String?, offset: Int?): ApiResponse<Listing>
}