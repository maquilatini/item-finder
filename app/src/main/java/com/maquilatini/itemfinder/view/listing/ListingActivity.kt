package com.maquilatini.itemfinder.view.listing

import android.app.SearchManager.QUERY
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maquilatini.itemfinder.R
import com.maquilatini.itemfinder.api.model.Listing
import com.maquilatini.itemfinder.api.service.listing.ListingService
import com.maquilatini.itemfinder.databinding.ActivityListingBinding
import com.maquilatini.itemfinder.databinding.ErrorLayoutBinding
import com.maquilatini.itemfinder.model.listing.ListingModel
import com.maquilatini.itemfinder.utils.CATEGORY_ID_KEY
import com.maquilatini.itemfinder.utils.ITEM_ID_KEY
import com.maquilatini.itemfinder.utils.toGone
import com.maquilatini.itemfinder.utils.toVisible
import com.maquilatini.itemfinder.view.item.ItemDetailActivity
import com.maquilatini.itemfinder.viewmodel.DeviceOffline
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import com.maquilatini.itemfinder.viewmodel.getViewModel
import com.maquilatini.itemfinder.viewmodel.listing.ListingViewModel

class ListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListingBinding
    private lateinit var bindingStub: ErrorLayoutBinding

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
        binding.listingStub.setOnInflateListener { _, inflated -> bindingStub = ErrorLayoutBinding.bind(inflated) }
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
                    loadItems(response.data)
                }
                is ErrorResponse -> {
                    handleError(getString(R.string.listing_error_message))
                }
                is DeviceOffline -> {
                    handleError(getString(R.string.listing_error_connection))
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

    private fun initView() {
        binding.listingRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListingActivity)
            adapter =
                ListingAdapter(this@ListingActivity, listingViewModel.getItems()) { itemId ->
                    goToItemDetail(itemId)
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

    private fun loadItems(listing: Listing) {
        binding.progressBarListing.toGone()
        if (listingViewModel.getItems().isEmpty() && listing.results.isEmpty()) {
            showError(getString(R.string.listing_empty_results), R.drawable.ic_empty_response)
        } else {
            binding.listingRecyclerView.toVisible()
            (binding.listingRecyclerView.adapter as ListingAdapter).addItems(listing.results)
        }
    }

    private fun handleError(message: String) {
        binding.progressBarListing.toGone()
        if (listingViewModel.getItems().isEmpty()) {
            binding.listingRecyclerView.toGone()
            showError(message, R.drawable.ic_error)
        } else {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showError(message: String, resId: Int) {
        binding.listingStub.inflate()
        bindingStub.errorTitleText.text = message
        bindingStub.errorImageView.setImageResource(resId)
    }

    private fun goToItemDetail(itemId: String) {
        val intent = Intent(this, ItemDetailActivity::class.java)
        intent.putExtra(ITEM_ID_KEY, itemId)
        startActivity(intent)
    }
}