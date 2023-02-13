package com.maquilatini.itemfinder.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

/**
 * Number Utils - To BigDecimal.
 */
object NumberUtils {

    private const val ES_LANGUAGE = "es"
    private const val ES_COUNTRY = "ES"

    //Checks whether 'this' has floating value and truncates it.
    private fun BigDecimal.priceTruncate(): BigDecimal = if (remainder(BigDecimal.ONE) > BigDecimal.ZERO) {
        setScale(2, RoundingMode.HALF_UP)
    } else {
        this
    }

    //Return a String that represents a price with Locale ES.
    // E.g. 4000 -> 4.000 - 10090.80 -> 10.090,80
    fun BigDecimal.getFormattedPrice(): String {
        val truncateValue = priceTruncate()
        val format = NumberFormat.getNumberInstance(Locale(ES_LANGUAGE, ES_COUNTRY))
        return if (truncateValue.stripTrailingZeros().scale() <= 0) {
            format.format(truncateValue.toInt())
        } else {
            format.maximumFractionDigits = 2
            format.minimumFractionDigits = 2
            format.format(truncateValue)
        }
    }
}