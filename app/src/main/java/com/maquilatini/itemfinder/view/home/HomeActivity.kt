package com.maquilatini.itemfinder.view.home

import android.app.SearchManager.QUERY
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maquilatini.itemfinder.R
import com.maquilatini.itemfinder.api.model.Category
import com.maquilatini.itemfinder.api.service.categories.CategoriesService
import com.maquilatini.itemfinder.databinding.ActivityHomeBinding
import com.maquilatini.itemfinder.databinding.ErrorLayoutBinding
import com.maquilatini.itemfinder.model.categories.CategoriesModel
import com.maquilatini.itemfinder.utils.AR_SITE_ID
import com.maquilatini.itemfinder.utils.CATEGORY_ID_KEY
import com.maquilatini.itemfinder.utils.CELLPHONES_CATEGORY_ID
import com.maquilatini.itemfinder.utils.REAL_ESTATE_CATEGORY_ID
import com.maquilatini.itemfinder.utils.SERVICES_CATEGORY_ID
import com.maquilatini.itemfinder.utils.VEHICLES_CATEGORY_ID
import com.maquilatini.itemfinder.utils.toGone
import com.maquilatini.itemfinder.utils.toVisible
import com.maquilatini.itemfinder.view.listing.ListingActivity
import com.maquilatini.itemfinder.viewmodel.DeviceOffline
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import com.maquilatini.itemfinder.viewmodel.getViewModel
import com.maquilatini.itemfinder.viewmodel.home.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var bindingStub: ErrorLayoutBinding
    private val homeViewModel: HomeViewModel by lazy {
        getViewModel {
            HomeViewModel(
                CategoriesModel(
                    CategoriesService()
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        binding.homeStub.setOnInflateListener { _, inflated -> bindingStub = ErrorLayoutBinding.bind(inflated) }
        setContentView(view)

        homeViewModel.categoriesLiveData.observe(this) { response ->
            when (response) {
                is Loading -> {
                    binding.progressBarCategories.toVisible()
                }
                is SuccessResponse -> {
                    binding.progressBarCategories.toGone()
                    binding.categoriesRecyclerView.toVisible()
                    loadCategories(response.data)
                }
                is ErrorResponse -> {
                    handleError(getString(R.string.home_error_message))
                }
                is DeviceOffline -> {
                    handleError(getString(R.string.home_error_connection))
                }
            }
        }

        initView()
    }

    private fun initView() {
        binding.apply {
            discountsCard.setOnClickListener {
                goToListing(categoryId = CELLPHONES_CATEGORY_ID)
            }

            //real estate
            featured1CategoryCard.setOnClickListener {
                goToListing(categoryId = REAL_ESTATE_CATEGORY_ID)
            }

            //vehicles
            featured2CategoryCard.setOnClickListener {
                goToListing(categoryId = VEHICLES_CATEGORY_ID)
            }

            //services
            featured3CategoryCard.setOnClickListener {
                goToListing(categoryId = SERVICES_CATEGORY_ID)
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    clearSearchView()
                    goToListing(query = query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // Not implementation required
                    return true
                }
            })
        }

        if (homeViewModel.getLoadedCategories().isEmpty()) {
            homeViewModel.getCategories(AR_SITE_ID)
        } else {
            loadCategories(homeViewModel.getLoadedCategories())
        }
    }

    private fun loadCategories(categories: List<Category>) {
        binding.categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter =
                CategoryAdapter(categories) { category ->
                    goToListing(categoryId = category.id)
                }

            val dividerItemDecoration = DividerItemDecoration(
                this.context,
                LinearLayoutManager.VERTICAL
            )
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun clearSearchView() {
        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()
    }

    private fun goToListing(categoryId: String? = null, query: String? = null) {
        val intent = Intent(this, ListingActivity::class.java)
        if (categoryId != null) {
            intent.putExtra(CATEGORY_ID_KEY, categoryId)
        } else if (query != null) {
            intent.putExtra(QUERY, query)
        }
        startActivity(intent)
    }

    private fun handleError(errorMessage: String) {
        binding.progressBarCategories.toGone()
        binding.categoriesRecyclerView.toGone()
        binding.categoriesText.toGone()
        binding.homeStub.inflate()
        bindingStub.errorTitleText.text = errorMessage
    }
}
