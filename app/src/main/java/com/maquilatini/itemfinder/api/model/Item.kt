package com.maquilatini.itemfinder.api.model

import java.math.BigDecimal
import java.util.Date
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class to represent a item.
 */
@Parcelize
data class Item(
    val id: String,
    val site_id: String,
    val title: String,
    val price: BigDecimal,
    val original_price: BigDecimal?,
    val currency_id: String,
    val available_quantity: Int,
    val sold_quantity: Int,
    val condition: String,
    val thumbnail: String,
    val accepts_mercadopago: Boolean,
    val stop_time: Date,
    val attributes: List<Attribute>
): Parcelable

@Parcelize
data class Attribute(
    val name: String,
    val value_name: String?,
    val id: String,
): Parcelable
