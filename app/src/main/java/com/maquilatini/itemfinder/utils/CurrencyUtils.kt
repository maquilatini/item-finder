package com.maquilatini.itemfinder.utils

/**
 * Currency Utils.
 */
object CurrencyUtils {

    private const val ARS = "ARS"
    private const val ARS_SYMBOL = "$"
    private const val USD_SYMBOL = "U\$D"

    //Returns the symbol of a currency id
    fun getCurrencySymbol(currencyId: String): String {
        return if (currencyId == ARS) ARS_SYMBOL else USD_SYMBOL
    }
}