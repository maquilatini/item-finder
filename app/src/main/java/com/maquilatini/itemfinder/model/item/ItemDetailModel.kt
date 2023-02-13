package com.maquilatini.itemfinder.model.item

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.ItemDescription
import com.maquilatini.itemfinder.api.model.ItemDetail
import com.maquilatini.itemfinder.api.service.item.IItemDetailService

/**
 * Class to represent an item detail model.
 */
class ItemDetailModel(private val service: IItemDetailService) :
    IItemDetailModel {
    override suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetail> {
        return service.getItemDetail(itemId)
    }

    override suspend fun getItemDescription(itemId: String): ApiResponse<ItemDescription> {
        return service.getItemDescription(itemId)
    }
}