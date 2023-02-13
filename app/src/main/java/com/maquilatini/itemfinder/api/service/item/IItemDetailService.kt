package com.maquilatini.itemfinder.api.service.item

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.ItemDescription
import com.maquilatini.itemfinder.api.model.ItemDetail

/**
 * Interface to item detail service.
 */
interface IItemDetailService {
    suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetail>
    suspend fun getItemDescription(itemId: String): ApiResponse<ItemDescription>
}