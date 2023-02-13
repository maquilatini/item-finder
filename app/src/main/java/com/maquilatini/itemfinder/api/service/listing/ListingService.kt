package com.maquilatini.itemfinder.api.service.listing

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.RetrofitFactory
import com.maquilatini.itemfinder.api.model.Listing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class to implement search result/listing service.
 */
class ListingService : IListingService {

    private val listingApi: ListingApi by lazy {
        RetrofitFactory.getServiceRetrofit(ListingApi::class.java)
    }

    override suspend fun search(query: String?, category: String?, offset: Int?): ApiResponse<Listing> {
        return withContext(Dispatchers.IO) {
            val response = listingApi.search(query, offset, category)
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResponse.Success(it)
                } ?: ApiResponse.Error(response.code(), response.message())
            } else {
                ApiResponse.Error(response.code(), response.message())
            }
        }
    }
}