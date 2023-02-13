package com.maquilatini.itemfinder.viewmodel.item

import androidx.lifecycle.LiveData
import com.maquilatini.itemfinder.api.model.ItemDescription
import com.maquilatini.itemfinder.api.model.ItemDetail
import com.maquilatini.itemfinder.viewmodel.ViewModelResponse

/**
 * Item Detail View Model Interface.
 */
interface IItemDetailViewModel {
    val itemDetailLiveData: LiveData<ViewModelResponse<ItemDetail>>
    val itemDescriptionLiveData: LiveData<ViewModelResponse<ItemDescription>>
    fun getItemDetail(itemId: String)
    fun getItemDescription(itemId: String)
}