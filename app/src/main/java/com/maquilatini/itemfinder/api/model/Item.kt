package com.maquilatini.itemfinder.api.model

import java.math.BigDecimal
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class to represent a item.
 */
@Parcelize
data class Item(
    val id: String,
    val title: String,
    val price: BigDecimal?,
    val currency_id: String?,
    val condition: String?,
    val thumbnail: String,
    val attributes: List<Attribute>
): Parcelable

@Parcelize
data class Attribute(
    val name: String,
    val value_name: String?,
    val id: String,
): Parcelable
