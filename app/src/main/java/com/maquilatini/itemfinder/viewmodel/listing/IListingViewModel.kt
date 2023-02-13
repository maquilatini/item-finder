package com.maquilatini.itemfinder.viewmodel.listing

import androidx.lifecycle.LiveData
import com.maquilatini.itemfinder.api.model.Listing
import com.maquilatini.itemfinder.viewmodel.ViewModelResponse

/**
 * Listing View Model Interface.
 */
interface IListingViewModel {
    val listingLiveData: LiveData<ViewModelResponse<Listing>>
    fun search(query: String? = null, categoryId: String? = null, offset: Int? = 0)
}