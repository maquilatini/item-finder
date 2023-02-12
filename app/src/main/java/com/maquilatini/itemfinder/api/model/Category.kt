package com.maquilatini.itemfinder.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class to represent a category.
 */
@Parcelize
data class Category(
    val id: String,
    val name: String
) : Parcelable