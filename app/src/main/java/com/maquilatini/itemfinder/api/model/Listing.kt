package com.maquilatini.itemfinder.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class to represent a search result.
 */
@Parcelize
data class Listing(
    val query: String,
    val paging: Paging,
    val results: List<Item>
) : Parcelable

@Parcelize
data class Paging(
    val total: Int,
    val offset: Int,
    val limit: Int,
) : Parcelable