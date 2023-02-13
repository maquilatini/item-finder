package com.maquilatini.itemfinder.api.model

import java.math.BigDecimal
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class to represent a item detail.
 */
@Parcelize
data class ItemDetail(
    val id: String,
    val title: String,
    val currency_id: String?,
    val price: BigDecimal?,
    val condition: String?,
    val thumbnail: String,
    val pictures: List<PictureDetail>,
    var attributes: List<Attribute>,
): Parcelable

@Parcelize
data class PictureDetail(
    val id: String,
    val url: String,
    val secure_url: String,
    val size: String,
    val max_size: String,
    val quality: String
): Parcelable

@Parcelize
data class ItemDescription(
    val plain_text: String
): Parcelable