package com.maquilatini.itemfinder.model.item

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.ItemDescription
import com.maquilatini.itemfinder.api.model.ItemDetail

/**
 * Interface to represent an item detail model.
 */
interface IItemDetailModel {
    suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetail>
    suspend fun getItemDescription(itemId: String): ApiResponse<ItemDescription>
}