package com.maquilatini.itemfinder.api.service.item

import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.RetrofitFactory
import com.maquilatini.itemfinder.api.model.ItemDescription
import com.maquilatini.itemfinder.api.model.ItemDetail
import com.maquilatini.itemfinder.api.service.BaseService
import com.maquilatini.itemfinder.api.service.parse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class to implement item detail service.
 */
class ItemDetailService : BaseService(), IItemDetailService {

    private val itemDetailApi: ItemDetailApi by lazy {
        RetrofitFactory.getServiceRetrofit(ItemDetailApi::class.java)
    }

    override suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetail> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext itemDetailApi.getItemDetail(itemId).parse()
            } catch (e: Exception) {
                return@withContext getApiError(e)
            }
        }
    }

    override suspend fun getItemDescription(itemId: String): ApiResponse<ItemDescription> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext itemDetailApi.getItemDescription(itemId).parse()
            } catch (e: Exception) {
                return@withContext getApiError(e)
            }
        }
    }
}
