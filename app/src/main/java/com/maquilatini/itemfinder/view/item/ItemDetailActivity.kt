package com.maquilatini.itemfinder.view.item

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.maquilatini.itemfinder.R
import com.maquilatini.itemfinder.api.model.ItemDetail
import com.maquilatini.itemfinder.api.service.item.ItemDetailService
import com.maquilatini.itemfinder.databinding.ActivityItemDetailBinding
import com.maquilatini.itemfinder.model.item.ItemDetailModel
import com.maquilatini.itemfinder.utils.CurrencyUtils
import com.maquilatini.itemfinder.utils.ITEM_ID_KEY
import com.maquilatini.itemfinder.utils.NumberUtils.getFormattedPrice
import com.maquilatini.itemfinder.utils.toGone
import com.maquilatini.itemfinder.utils.toVisible
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import com.maquilatini.itemfinder.viewmodel.getViewModel
import com.maquilatini.itemfinder.viewmodel.item.ItemDetailViewModel

class ItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetailBinding

    private val itemDetailViewModel: ItemDetailViewModel by lazy {
        getViewModel {
            ItemDetailViewModel(ItemDetailModel(ItemDetailService()))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Item Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (intent.hasExtra(ITEM_ID_KEY)) {
            intent.getStringExtra(ITEM_ID_KEY)?.let { itemId ->
                itemDetailViewModel.getItemDetail(itemId)
                itemDetailViewModel.getItemDescription(itemId)
            }
        }

        itemDetailViewModel.itemDetailLiveData.observe(this) { response ->
            when (response) {
                is Loading -> {
                    binding.progressBarItemDetail.toVisible()
                }
                is SuccessResponse -> {
                    binding.progressBarItemDetail.toGone()
                    setItemDetail(response.data)
                }
                is ErrorResponse -> {
                    binding.progressBarItemDetail.toGone()
                    // TODO complete error message
                }
            }
        }

        itemDetailViewModel.itemDescriptionLiveData.observe(this) { response ->
            when (response) {
                is Loading -> {
                }
                is SuccessResponse -> {
                    setItemDescription(response.data.plain_text)
                }
                is ErrorResponse -> {
                    setItemDescription(getString(R.string.item_no_description))
                }
            }
        }
    }

    private fun setItemDetail(itemDetail: ItemDetail) {
        binding.itemTitleText.text = itemDetail.title
        binding.imageSeparator.toVisible()

        if (itemDetail.price != null) {
            binding.itemPriceText.text = getString(
                R.string.listing_item_price,
                CurrencyUtils.getCurrencySymbol(itemDetail.currency_id),
                itemDetail.price.getFormattedPrice()
            )
        } else {
            binding.itemPriceText.text = getString(R.string.listing_no_price)
        }

        if (itemDetail.condition != null) {
            binding.itemConditionText.toVisible()
            binding.itemConditionText.text = itemDetail.condition
        } else {
            binding.itemConditionText.toGone()
        }

        val imageUrl = itemDetail.pictures[0].secure_url
        Glide.with(this).load(imageUrl).centerInside()
            .error(R.drawable.ic_no_photo)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.itemImageView)
    }

    private fun setItemDescription(description: String) {
        binding.itemDescriptionValueText.text = description
        binding.itemDescriptionValueText.toVisible()
        binding.descriptionSeparator.toVisible()
        binding.itemDescriptionText.toVisible()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}