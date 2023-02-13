package com.maquilatini.itemfinder.viewmodel.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.ItemDescription
import com.maquilatini.itemfinder.api.model.ItemDetail
import com.maquilatini.itemfinder.model.item.IItemDetailModel
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import com.maquilatini.itemfinder.viewmodel.ViewModelResponse
import kotlinx.coroutines.launch

/**
 * Item Detail View Model.
 */
class ItemDetailViewModel(private val model: IItemDetailModel) : ViewModel(),
    IItemDetailViewModel {

    private var _itemDetailLiveData: MutableLiveData<ViewModelResponse<ItemDetail>> =
        MutableLiveData()
    private var _itemDescriptionLiveData: MutableLiveData<ViewModelResponse<ItemDescription>> =
        MutableLiveData()
    override val itemDetailLiveData: LiveData<ViewModelResponse<ItemDetail>> = _itemDetailLiveData
    override val itemDescriptionLiveData: LiveData<ViewModelResponse<ItemDescription>> = _itemDescriptionLiveData

    override fun getItemDetail(itemId: String) {
        viewModelScope.launch {
            _itemDetailLiveData.postValue(Loading(true))

            when (val result = model.getItemDetail(itemId)) {
                is ApiResponse.Success -> {
                    _itemDetailLiveData.value = SuccessResponse(result.data)
                }
                is ApiResponse.Error -> {
                    _itemDetailLiveData.value = ErrorResponse(result.code, result.message)
                }
                is ApiResponse.NetworkError -> {
                    // TODO
                }
            }
        }
    }

    override fun getItemDescription(itemId: String) {
        viewModelScope.launch {
            _itemDescriptionLiveData.postValue(Loading(true))

            when (val result = model.getItemDescription(itemId)) {
                is ApiResponse.Success -> {
                    _itemDescriptionLiveData.value = SuccessResponse(result.data)
                }
                is ApiResponse.Error -> {
                    _itemDescriptionLiveData.value = ErrorResponse(result.code, result.message)
                }
                is ApiResponse.NetworkError -> {
                    // TODO
                }
            }
        }
    }
}