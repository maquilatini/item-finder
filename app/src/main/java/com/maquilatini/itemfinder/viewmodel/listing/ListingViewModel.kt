package com.maquilatini.itemfinder.viewmodel.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Item
import com.maquilatini.itemfinder.api.model.Listing
import com.maquilatini.itemfinder.model.listing.IListingModel
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import com.maquilatini.itemfinder.viewmodel.ViewModelResponse
import kotlinx.coroutines.launch

class ListingViewModel(private val model: IListingModel) : ViewModel(),
    IListingViewModel {

    private val _listingLiveData: MutableLiveData<ViewModelResponse<Listing>> = MutableLiveData()
    override val listingLiveData: LiveData<ViewModelResponse<Listing>> = _listingLiveData
    private var loadedItems: MutableList<Item> = mutableListOf()
    private var offset: Int = 0
    private var total: Int = 0
    private var limit: Int = 0
    private var lastQuery: String? = ""
    private var lastCategory: String? = ""
    private var searchInProgress = false
    private var loadMore: Boolean = true
    private var searchByQuery: Boolean = false

    override fun search(query: String?, categoryId: String?, currentOffset: Int?) {
        viewModelScope.launch {
            _listingLiveData.postValue(Loading(true))

            loadMore = false
            lastQuery = query
            lastCategory = categoryId
            when (val result = model.search(query = query, categoryId = categoryId, offset = currentOffset)) {
                is ApiResponse.Success -> {
                    searchInProgress = true
                    result.data.apply {
                        _listingLiveData.value = SuccessResponse(this)
                        loadedItems.addAll(this.results)
                        offset = this.paging.offset
                        total = this.paging.total
                        limit = this.paging.limit
                    }
                }

                is ApiResponse.Error -> {
                    _listingLiveData.value = ErrorResponse(result.code, result.message)
                    searchInProgress = false
                }
                is ApiResponse.NetworkError -> {
                    // TODO
                }
            }
            loadMore = true
        }
    }

    fun searchNewPage() {
        //If has more items
        if ((offset + limit < total && offset <= 1000) && loadMore) {
            val newOffset = offset + limit
            if (searchByQuery) {
                search(query = lastQuery, currentOffset = newOffset)
            } else {
                search(categoryId = lastCategory, currentOffset = newOffset)
            }
        }
    }

    fun isSearchInProgress(): Boolean {
        return searchInProgress
    }

    fun setSearchByQuery(isByQuery: Boolean) {
        searchByQuery = isByQuery
    }

    fun getItems(): MutableList<Item> {
        return loadedItems
    }
}