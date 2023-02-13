package com.maquilatini.itemfinder.api.service.item

import com.maquilatini.itemfinder.api.model.ItemDescription
import com.maquilatini.itemfinder.api.model.ItemDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface to item detail API.
 */
interface ItemDetailApi {
    @GET("/items/{itemId}")
    suspend fun getItemDetail(@Path("itemId") itemId: String): Response<ItemDetail>

    @GET("/items/{itemId}/description")
    suspend fun getItemDescription(@Path("itemId") itemId: String): Response<ItemDescription>
}