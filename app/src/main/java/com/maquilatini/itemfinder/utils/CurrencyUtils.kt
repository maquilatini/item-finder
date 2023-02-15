package com.maquilatini.itemfinder.utils

/**
 * Currency Utils.
 */
object CurrencyUtils {

    //Returns the symbol of a currency id
    fun getCurrencySymbol(currencyId: String?): String {
        return if (currencyId == USD) USD_SYMBOL else ARS_SYMBOL
    }
}