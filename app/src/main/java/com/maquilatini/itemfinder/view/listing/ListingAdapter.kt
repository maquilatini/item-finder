package com.maquilatini.itemfinder.view.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maquilatini.itemfinder.R
import com.maquilatini.itemfinder.api.model.Item
import com.maquilatini.itemfinder.databinding.ItemListingLayoutBinding
import com.maquilatini.itemfinder.utils.CurrencyUtils
import com.maquilatini.itemfinder.utils.NumberUtils.getFormattedPrice

class ListingAdapter(
    private val context: Context,
    private val itemsList: MutableList<Item> = mutableListOf(),
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<ListingAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemListingLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val binding = ItemListingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemsList[position]
        holder.apply {
            binding.titleText.text = item.title
            binding.priceText.text = context.getString(
                R.string.listing_item_price,
                CurrencyUtils.getCurrencySymbol(item.currency_id),
                item.price.getFormattedPrice()
            )
            binding.conditionText.text = item.condition

            Glide.with(holder.itemView.context).load(item.thumbnail).centerInside()
                .error(R.drawable.ic_no_photo)
                .dontAnimate().into(binding.itemImageView)

            binding.itemListingContainer.setOnClickListener {
                onItemClicked(item.id)
            }
        }
    }

    fun addItems(newItemsList: List<Item>) {
        val oldSize = itemsList.size
        itemsList.addAll(newItemsList)
        notifyItemRangeInserted(oldSize, itemsList.size)
    }
}