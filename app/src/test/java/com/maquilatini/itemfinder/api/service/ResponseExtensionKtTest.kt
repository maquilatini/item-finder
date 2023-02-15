package com.maquilatini.itemfinder.api.service

import com.maquilatini.itemfinder.api.ApiResponse
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class ResponseExtensionKtTest {

    @Test
    fun test_parse_with_successful_response() {
        // Given
        val data = "body"
        val response = Response.success(data)

        // Then
        val result = response.parse()

        // Assert
        assertTrue(result is ApiResponse.Success)
        assertEquals(data, (result as ApiResponse.Success).data)
    }

    @Test
    fun test_parse_with_error_response() {
        // Given
        val code = 404
        val response = Response.error<String>(code, ResponseBody.create(MediaType.parse("text/plain"), "content"))

        // Then
        val result = response.parse()

        // Assert
        assertTrue(result is ApiResponse.Error)
        assertEquals(code, (result as ApiResponse.Error).code)
    }

    @Test
    fun test_parse_with_successful_response_without_body() {
        // Given
        val code = 200
        val response = Response.success<String>(null)

        // Then
        val result = response.parse()

        // Assert
        assertTrue(result is ApiResponse.Error)
        assertEquals(code, (result as ApiResponse.Error).code)
    }
}
