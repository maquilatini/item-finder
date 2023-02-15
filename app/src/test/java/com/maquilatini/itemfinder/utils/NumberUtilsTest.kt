package com.maquilatini.itemfinder.utils

import com.maquilatini.itemfinder.utils.NumberUtils.getFormattedPrice
import java.math.BigDecimal
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * NumberUtilsTest uses getFormattedPrice() that should format the price correctly for *ES* locale
 */
class NumberUtilsTest {

    @Test
    fun test_getFormattedPrice_with_correct_format() {
        // Given
        val price = BigDecimal("200")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("200", formattedPrice)
    }

    @Test
    fun test_getFormattedPrice_with_thousand_separator() {
        // Given
        val price = BigDecimal("8000")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("8.000", formattedPrice)
    }


    @Test
    fun test_getFormattedPrice_with_thousand_separator_in_millionNumber() {
        // Given
        val price = BigDecimal("2500000")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("2.500.000", formattedPrice)
    }

    @Test
    fun test_getFormattedPrice_with_thousand_and_decimal_separator() {
        // Given
        val price = BigDecimal("2500000.28")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("2.500.000,28", formattedPrice)
    }

    @Test
    fun test_getFormattedPrice_without_rounding() {
        // Given
        val price = BigDecimal("0.99")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("0,99", formattedPrice)
    }

    @Test
    fun test_getFormattedPrice_by_rounding() {
        // Given
        val price = BigDecimal("0.998")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("1", formattedPrice)
    }

    @Test
    fun test_getFormattedPrice_by_truncating() {
        // Given
        val price = BigDecimal("0.912")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("0,91", formattedPrice)
    }

    @Test
    fun test_getFormattedPrice_with_zero_value() {
        // Given
        val price = BigDecimal("0.000")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("0", formattedPrice)
    }

    @Test
    fun test_getFormattedPrice_with_zero_value_by_rounding() {
        // Given
        val price = BigDecimal("0.001")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("0", formattedPrice)
    }

    @Test
    fun test_getFormattedPrice_with_more_than_two_decimals() {
        // Given
        val price = BigDecimal("0.9800")

        // Then
        val formattedPrice = price.getFormattedPrice()

        // Assert
        assertEquals("0,98", formattedPrice)
    }
}