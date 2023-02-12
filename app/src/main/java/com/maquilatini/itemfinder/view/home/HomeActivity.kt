package com.maquilatini.itemfinder.view.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maquilatini.itemfinder.api.model.Category
import com.maquilatini.itemfinder.api.service.categories.CategoriesService
import com.maquilatini.itemfinder.databinding.ActivityHomeBinding
import com.maquilatini.itemfinder.model.categories.CategoriesModel
import com.maquilatini.itemfinder.utils.AR_SITE_ID
import com.maquilatini.itemfinder.utils.CATEGORY_ID_KEY
import com.maquilatini.itemfinder.utils.CATEGORY_NAME_KEY
import com.maquilatini.itemfinder.utils.CELLPHONES_CATEGORY_ID
import com.maquilatini.itemfinder.utils.REAL_ESTATE_CATEGORY_ID
import com.maquilatini.itemfinder.utils.SERVICES_CATEGORY_ID
import com.maquilatini.itemfinder.utils.VEHICLES_CATEGORY_ID
import com.maquilatini.itemfinder.utils.toGone
import com.maquilatini.itemfinder.utils.toVisible
import com.maquilatini.itemfinder.view.listing.ListingActivity
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import com.maquilatini.itemfinder.viewmodel.getViewModel
import com.maquilatini.itemfinder.viewmodel.home.CategoriesViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val categoriesViewModel: CategoriesViewModel by lazy {
        getViewModel {
            CategoriesViewModel(
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
        setContentView(view)

        categoriesViewModel.categoriesLiveData.observe(this) { response ->
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
                    binding.progressBarCategories.toGone()
                    binding.categoriesRecyclerView.toGone()
                    // TODO complete error message
                }
            }
        }

        initView()
    }

    private fun initView() {
        binding.apply {
            discountsCard.setOnClickListener {
                goToListing(CELLPHONES_CATEGORY_ID)
            }

            //realestate
            featured1CategoryCard.setOnClickListener {
                goToListing(REAL_ESTATE_CATEGORY_ID)
            }

            //vehicles
            featured2CategoryCard.setOnClickListener {
                goToListing(VEHICLES_CATEGORY_ID)
            }

            //services
            featured3CategoryCard.setOnClickListener {
                goToListing(SERVICES_CATEGORY_ID)
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    // TODO go listing
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // Not implementation required
                    return true
                }
            })
        }

        if (categoriesViewModel.getCategories().isEmpty()) {
            categoriesViewModel.getCategories(AR_SITE_ID)
        } else {
            loadCategories(categoriesViewModel.getCategories())
        }
    }

    private fun loadCategories(categories: List<Category>) {
        binding.categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter =
                CategoryAdapter(categories) { category ->
                    goToListing(category.id, category.name)
                }

            val dividerItemDecoration = DividerItemDecoration(
                this.context,
                LinearLayoutManager.VERTICAL
            )
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun goToListing(categoryId: String, categoryName: String? = null) {
        val intent = Intent(this, ListingActivity::class.java)
        intent.putExtra(CATEGORY_ID_KEY, categoryId)
        intent.putExtra(CATEGORY_NAME_KEY, categoryName)
        startActivity(intent)
    }
}