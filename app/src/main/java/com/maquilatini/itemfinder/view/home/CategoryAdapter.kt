package com.maquilatini.itemfinder.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maquilatini.itemfinder.api.model.Category
import com.maquilatini.itemfinder.databinding.ItemCategoryLayoutBinding

class CategoryAdapter(private val categoryList: List<Category>, private val onItemClicked: (Category) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: ItemCategoryLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val binding = ItemCategoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.apply {
            binding.categoryText.text = category.name
            binding.categoryContainer.setOnClickListener {
                onItemClicked(category)
            }
        }
    }
}