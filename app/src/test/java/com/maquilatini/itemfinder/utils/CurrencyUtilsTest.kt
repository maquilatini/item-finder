package com.maquilatini.itemfinder.utils

import junit.framework.Assert.assertEquals
import org.junit.Test

class CurrencyUtilsTest {


    // If currencyId is null should return ARS_SYMBOL because the default SITE is ARG
    @Test
    fun test_getCurrencySymbol_with_null_currencyId() {
        // Given
        val currencyId = null

        // Then
        val currencySymbol = CurrencyUtils.getCurrencySymbol(currencyId)

        // Assert
        assertEquals(ARS_SYMBOL, currencySymbol)
    }

    @Test
    fun test_getCurrencySymbol_with_ARS_currencyId() {
        // Given
        val currencyId = "ARS"

        // Then
        val currencySymbol = CurrencyUtils.getCurrencySymbol(currencyId)

        // Assert
        assertEquals(ARS_SYMBOL, currencySymbol)
    }

    @Test
    fun test_getCurrencySymbol_with_USD_currencyId() {
        // Given
        val currencyId = "USD"

        // Then
        val currencySymbol = CurrencyUtils.getCurrencySymbol(currencyId)

        // Assert
        assertEquals(USD_SYMBOL, currencySymbol)
    }

    @Test
    fun test_getCurrencySymbol_with_invalid_currencyId() {
        // Given
        val invalidCurrencyId = "USD"

        // Then
        val currencySymbol = CurrencyUtils.getCurrencySymbol(invalidCurrencyId)

        // Assert
        assertEquals(ARS_SYMBOL, currencySymbol)
    }
}