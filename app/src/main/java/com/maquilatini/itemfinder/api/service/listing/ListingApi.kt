package com.maquilatini.itemfinder.api.service.listing

import com.maquilatini.itemfinder.api.model.Listing
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface to search result/listing API.
 */
interface ListingApi {
    @GET("/sites/MLA/search")
    suspend fun search(@Query("q") query: String? = "", @Query("offset") offset: Int? = 0, @Query("category") category: String? = ""): Response<Listing>
}