package com.maquilatini.itemfinder.api.service.listing

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.RetrofitFactory
import com.maquilatini.itemfinder.api.model.Listing
import com.maquilatini.itemfinder.api.service.BaseService
import com.maquilatini.itemfinder.api.service.parse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class to implement search result/listing service.
 */
class ListingService : BaseService(), IListingService {

    private val listingApi: ListingApi by lazy {
        RetrofitFactory.getServiceRetrofit(ListingApi::class.java)
    }

    override suspend fun search(query: String?, category: String?, offset: Int?): ApiResponse<Listing> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext listingApi.search(query, offset, category).parse()
            } catch (e: Exception) {
                return@withContext getApiError(e)
            }
        }
    }
}