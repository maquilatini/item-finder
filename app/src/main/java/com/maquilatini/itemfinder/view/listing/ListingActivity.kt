package com.maquilatini.itemfinder.view.listing

import android.app.SearchManager.QUERY
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maquilatini.itemfinder.api.model.Listing
import com.maquilatini.itemfinder.api.service.listing.ListingService
import com.maquilatini.itemfinder.databinding.ActivityListingBinding
import com.maquilatini.itemfinder.model.listing.ListingModel
import com.maquilatini.itemfinder.utils.CATEGORY_ID_KEY
import com.maquilatini.itemfinder.utils.toGone
import com.maquilatini.itemfinder.utils.toVisible
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import com.maquilatini.itemfinder.viewmodel.getViewModel
import com.maquilatini.itemfinder.viewmodel.listing.ListingViewModel

class ListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListingBinding

    private val listingViewModel: ListingViewModel by lazy {
        getViewModel {
            ListingViewModel(
                ListingModel(
                    ListingService()
                )
            )
        }
    }

    companion object {
        private const val VERTICAL_DIRECTION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (!listingViewModel.isSearchInProgress()) {
            if (intent.hasExtra(QUERY)) {
                intent.getStringExtra(QUERY)?.let { query ->
                    search(query)
                }
            } else if (intent.hasExtra(CATEGORY_ID_KEY)) {
                intent.getStringExtra(CATEGORY_ID_KEY)?.let { searchByCategory(it) }
            }
        }

        listingViewModel.listingLiveData.observe(this) { response ->
            when (response) {
                is Loading -> {
                    binding.progressBarListing.toVisible()
                }
                is SuccessResponse -> {
                    binding.progressBarListing.toGone()
                    binding.listingRecyclerView.toVisible()
                    loadItems(response.data)
                }
                is ErrorResponse -> {
                    binding.progressBarListing.toGone()
                    binding.listingRecyclerView.toGone()
                    // TODO complete error message
                }
            }
        }

        initView()
    }

    private fun search(query: String) {
        listingViewModel.setSearchByQuery(true)
        listingViewModel.search(query = query)
    }

    private fun searchByCategory(categoryId: String) {
        listingViewModel.setSearchByQuery(false)
        listingViewModel.search(categoryId = categoryId)
    }

    private fun loadItems(listing: Listing) {
        (binding.listingRecyclerView.adapter as ListingAdapter).addItems(listing.results)
    }

    private fun initView() {
        binding.listingRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListingActivity)
            adapter =
                ListingAdapter(this@ListingActivity, listingViewModel.getItems()) { itemId ->
                    //goToItemDetail(itemId)
                }

            val dividerItemDecoration = DividerItemDecoration(
                this.context,
                LinearLayoutManager.VERTICAL
            )
            addItemDecoration(dividerItemDecoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    //If the end of the recycler is reached, get new page
                    if (!recyclerView.canScrollVertically(VERTICAL_DIRECTION)) {
                        listingViewModel.searchNewPage()
                    }
                }
            })
        }
    }
}