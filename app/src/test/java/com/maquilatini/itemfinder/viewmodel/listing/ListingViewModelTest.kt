package com.maquilatini.itemfinder.viewmodel.listing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maquilatini.itemfinder.MainDispatcherRule
import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Item
import com.maquilatini.itemfinder.api.model.Listing
import com.maquilatini.itemfinder.api.model.Paging
import com.maquilatini.itemfinder.model.listing.IListingModel
import com.maquilatini.itemfinder.observeOnce
import com.maquilatini.itemfinder.viewmodel.DeviceOffline
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import java.math.BigDecimal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class ListingViewModelTest {

    private lateinit var mockListingModel: IListingModel
    private lateinit var listingViewModel: ListingViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockListingModel = mock(IListingModel::class.java)
        listingViewModel = ListingViewModel(mockListingModel)
    }


    @Test
    fun test_search_with_success_response() = runTest {
        // Given
        val query = "smartphones"
        val expectedResponse = Listing(
            paging = Paging(3, 50, 1000),
            results = listOf(
                Item(id = "MLA2", title = "Samsung Galaxy", condition = "new", currency_id = "ARS", price = BigDecimal(450000), thumbnail = ""),
                Item(id = "MLA3", title = "Xiaomi", condition = "used", currency_id = "ARS", price = BigDecimal(40000), thumbnail = ""),
            ),
            query = query
        )
        val successResult = ApiResponse.Success(expectedResponse)

        `when`(mockListingModel.search(eq(query), any(), any())).thenReturn(successResult)

        listingViewModel.search(query, null, null)

        listingViewModel.listingLiveData.observeOnce { response ->
            when (response) {
                is SuccessResponse -> {
                    assertTrue(response.data.results.isNotEmpty())
                    assertEquals(2, response.data.results.size)
                    assertTrue(listingViewModel.getItems().isNotEmpty())
                }
                else -> Assert.fail("The response should be SuccessResponse")
            }
        }
    }

    @Test
    fun test_search_with_error_response() = runTest {

        val query = "macbook pro"
        val mockErrorResponse = ApiResponse.Error(404, "Not Found")

        `when`(mockListingModel.search(eq(query), any(), any())).thenReturn(mockErrorResponse)

        listingViewModel.search(query, null, null)

        listingViewModel.listingLiveData.observeOnce { response ->
            when (response) {
                is ErrorResponse -> {
                    assertEquals(mockErrorResponse.code, response.code)
                    assertEquals(mockErrorResponse.message, response.message)
                    assertTrue(listingViewModel.getItems().isEmpty())
                }
                else -> Assert.fail("The response should be ErrorResponse")
            }
        }
    }

    @Test
    fun test_search_should_return_SuccessResponse_when_search_is_successful() = runTest {
        // Given
        val query = "smartphones"
        val expectedResponse = Listing(
            paging = Paging(3, 50, 1000),
            results = listOf(
                Item(id = "MLA1", title = "iPhone 11", condition = "used", currency_id = "ARS", price = BigDecimal(800000), thumbnail = ""),
                Item(id = "MLA2", title = "Samsung Galaxy", condition = "new", currency_id = "ARS", price = BigDecimal(450000), thumbnail = ""),
                Item(id = "MLA3", title = "Xiaomi", condition = "used", currency_id = "ARS", price = BigDecimal(40000), thumbnail = ""),
            ),
            query = query
        )
        val successResult = ApiResponse.Success(expectedResponse)

        `when`(mockListingModel.search(eq(query), any(), any())).thenReturn(successResult)

        // Then
        listingViewModel.search(query, null, null)

        // Assert
        assertEquals(SuccessResponse(expectedResponse), listingViewModel.listingLiveData.value)
        assertTrue(listingViewModel.isSearchInProgress())
        assertEquals(expectedResponse.results.size, listingViewModel.getItems().size)
    }

    @Test
    fun test_search_should_set_ErrorResponse_state_when_API_call_returns_error() = runTest {
        // Given
        val query = "macbook"
        val errorResult = ApiResponse.Error(404, "Not Found")
        val errorResponse = ErrorResponse<String>(404, "Not Found")

        `when`(mockListingModel.search(eq(query), any(), any())).thenReturn(errorResult)

        // Then
        listingViewModel.search(query, null, null)

        // Assert
        assertEquals(errorResponse, listingViewModel.listingLiveData.value)
        assertFalse(listingViewModel.isSearchInProgress())
    }

    @Test
    fun test_search_should_set_DeviceOffline_state_when_there_is_a_network_error() = runTest {
        // Given
        val query = "macbook"
        val networkErrorResult = ApiResponse.NetworkError("Network error")
        val networkError = DeviceOffline<String>("Network error")

        `when`(mockListingModel.search(eq(query), any(), any())).thenReturn(networkErrorResult)

        // Then
        listingViewModel.search(query, null, null)

        // Assert
        assertEquals(networkError, listingViewModel.listingLiveData.value)
    }

    @Test
    fun test_search_should_set_Loading_state_before_API_call() {
        // Given
        val categoryId = "MLA1"
        val loadingState = Loading<Boolean>(true)

        // Then
        listingViewModel.search(categoryId = categoryId)

        // Assert
        assertEquals(loadingState, listingViewModel.listingLiveData.value)
    }
}