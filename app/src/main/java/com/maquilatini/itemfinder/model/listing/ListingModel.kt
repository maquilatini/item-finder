package com.maquilatini.itemfinder.model.listing

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Listing
import com.maquilatini.itemfinder.api.service.listing.IListingService

/**
 * Class to represent a search result/listing model.
 */
class ListingModel(private val service: IListingService) :
    IListingModel {
    override suspend fun search(query: String?, categoryId: String?, offset: Int?): ApiResponse<Listing> {
        return service.search(query, categoryId, offset)
    }
}