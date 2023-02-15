package com.maquilatini.itemfinder.viewmodel.item

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maquilatini.itemfinder.MainDispatcherRule
import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.ItemDescription
import com.maquilatini.itemfinder.api.model.ItemDetail
import com.maquilatini.itemfinder.model.item.ItemDetailModel
import com.maquilatini.itemfinder.observeOnce
import com.maquilatini.itemfinder.viewmodel.DeviceOffline
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import java.math.BigDecimal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class ItemDetailViewModelTest {

    private lateinit var mockItemDetailModel: ItemDetailModel
    private lateinit var itemDetailViewModel: ItemDetailViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockItemDetailModel = mock(ItemDetailModel::class.java)
        itemDetailViewModel = ItemDetailViewModel(mockItemDetailModel)
    }

    @Test
    fun test_getItemDescription_with_success_response() = runTest {
        // Given
        val mockSuccessResponse =
            ApiResponse.Success(ItemDescription("Item description plain text"))

        `when`(mockItemDetailModel.getItemDescription(anyString())).thenReturn(mockSuccessResponse)

        // Then
        itemDetailViewModel.getItemDescription("MLA112")

        // Assert - Check the categoriesLiveData was set with the SuccessResponse
        itemDetailViewModel.itemDescriptionLiveData.observeOnce { response ->
            when (response) {
                is SuccessResponse -> {
                    assertEquals(mockSuccessResponse.data.plain_text, response.data.plain_text)
                    assertTrue(response.data.plain_text.isNotEmpty())
                }
                else -> fail("The response should be SuccessResponse")
            }
        }
    }

    @Test
    fun test_getItemDetail_with_success_response() = runTest {
        // Given
        val mockSuccessResponse =
            ApiResponse.Success(ItemDetail("MLA112", "Samsung Galaxy", "ARS", BigDecimal(200000), "new", "", listOf(), listOf()))

        `when`(mockItemDetailModel.getItemDetail(anyString())).thenReturn(mockSuccessResponse)

        // Then
        itemDetailViewModel.getItemDetail("MLA112")

        // Assert - Check the categoriesLiveData was set with the SuccessResponse
        itemDetailViewModel.itemDetailLiveData.observeOnce { response ->
            when (response) {
                is SuccessResponse -> {
                    assertEquals(mockSuccessResponse.data.id, response.data.id)
                    assertEquals(mockSuccessResponse.data.title, response.data.title)
                    assertEquals(mockSuccessResponse.data.currency_id, response.data.currency_id)
                    assertEquals(mockSuccessResponse.data.price, response.data.price)
                    assertEquals(mockSuccessResponse.data.condition, response.data.condition)
                    assertTrue(response.data.pictures.isEmpty())
                }
                else -> fail("The response should be SuccessResponse")
            }
        }
    }

    @Test
    fun test_getItemDetail_with_error_response() = runTest {
        // Given
        val mockErrorResponse = ApiResponse.Error(404, "Not Found")

        `when`(mockItemDetailModel.getItemDetail(anyString())).thenReturn(mockErrorResponse)

        // Then
        itemDetailViewModel.getItemDetail("MLA123")

        // Assert - Verify the categoriesLiveData was set with the ErrorResponse
        itemDetailViewModel.itemDetailLiveData.observeOnce { response ->
            when (response) {
                is ErrorResponse -> {
                    assertEquals(mockErrorResponse.code, response.code)
                    assertEquals(mockErrorResponse.message, response.message)
                }
                else -> fail("The response should be ErrorResponse")
            }
        }
    }

    @Test
    fun test_getItemDescription_with_error_response() = runTest {
        // Given
        val mockErrorResponse = ApiResponse.Error(404, "Not Found")

        `when`(mockItemDetailModel.getItemDescription(anyString())).thenReturn(mockErrorResponse)

        // Then
        itemDetailViewModel.getItemDescription("MLA123")

        // Assert - Verify the categoriesLiveData was set with the ErrorResponse
        itemDetailViewModel.itemDescriptionLiveData.observeOnce { response ->
            when (response) {
                is ErrorResponse -> {
                    assertEquals(mockErrorResponse.code, response.code)
                    assertEquals(mockErrorResponse.message, response.message)
                }
                else -> fail("The response should be ErrorResponse")
            }
        }
    }

    @Test
    fun test_getItemDetail_should_set_Loading_state_before_API_call() {
        // Given
        val itemId = "MLA123"
        val loadingState = Loading<Boolean>(true)

        // Then
        itemDetailViewModel.getItemDetail(itemId)

        // Assert
        assertEquals(loadingState, itemDetailViewModel.itemDetailLiveData.value)
    }

    @Test
    fun test_getItemDescription_should_set_Loading_state_before_API_call() {
        // Given
        val itemId = "MLA123"
        val loadingState = Loading<Boolean>(true)

        // Then
        itemDetailViewModel.getItemDescription(itemId)

        // Assert
        assertEquals(loadingState, itemDetailViewModel.itemDescriptionLiveData.value)
    }

    @Test
    fun test_getItemDetail_should_set_SuccessResponse_state_when_API_call_is_successful() = runTest {
        // Given
        val itemId = "MLA123"
        val itemDetail = ItemDetail("MLA112", "Samsung Galaxy", "ARS", BigDecimal(200000), "new", "", listOf(), listOf())
        val successResult = ApiResponse.Success(itemDetail)
        `when`(mockItemDetailModel.getItemDetail(itemId)).thenReturn(successResult)

        // Then
        itemDetailViewModel.getItemDetail(itemId)

        // Assert
        assertEquals(SuccessResponse(itemDetail), itemDetailViewModel.itemDetailLiveData.value)
    }

    @Test
    fun test_getItemDescription_should_set_SuccessResponse_state_when_API_call_is_successful() = runTest {
        // Given
        val itemId = "MLA123"
        val itemDescription = ItemDescription("Item description plain text")
        val successResult = ApiResponse.Success(itemDescription)
        `when`(mockItemDetailModel.getItemDescription(itemId)).thenReturn(successResult)

        // Then
        itemDetailViewModel.getItemDescription(itemId)

        // Assert
        assertEquals(SuccessResponse(itemDescription), itemDetailViewModel.itemDescriptionLiveData.value)
    }

    @Test
    fun test_getItemDetail_should_set_ErrorResponse_state_when_API_call_returns_error() = runTest {
        // Given
        val itemId = "MLA123"
        val errorResult = ApiResponse.Error(404, "Not Found")
        val errorResponse = ErrorResponse<String>(404, "Not Found")

        `when`(mockItemDetailModel.getItemDetail(itemId)).thenReturn(errorResult)

        // Then
        itemDetailViewModel.getItemDetail(itemId)

        // Assert
        assertEquals(errorResponse, itemDetailViewModel.itemDetailLiveData.value)
    }

    @Test
    fun test_getItemDescription_should_set_ErrorResponse_state_when_API_call_returns_error() = runTest {
        // Given
        val itemId = "MLA123"
        val errorResult = ApiResponse.Error(404, "Not Found")
        val errorResponse = ErrorResponse<String>(404, "Not Found")

        `when`(mockItemDetailModel.getItemDescription(itemId)).thenReturn(errorResult)

        // Then
        itemDetailViewModel.getItemDescription(itemId)

        // Assert
        assertEquals(errorResponse, itemDetailViewModel.itemDescriptionLiveData.value)
    }

    @Test
    fun test_getItemDetail_should_set_DeviceOffline_state_when_there_is_a_network_error() = runTest {
        // Given
        val itemId = "MLA123"
        val networkErrorResult = ApiResponse.NetworkError("Network error")
        val networkError = DeviceOffline<String>("Network error")

        `when`(mockItemDetailModel.getItemDetail(itemId)).thenReturn(networkErrorResult)

        // Then
        itemDetailViewModel.getItemDetail(itemId)

        // Assert
        assertEquals(networkError, itemDetailViewModel.itemDetailLiveData.value)
    }

    @Test
    fun test_getItemDescription_should_set_DeviceOffline_state_when_there_is_a_network_error() = runTest {
        // Given
        val itemId = "MLA123"
        val networkErrorResult = ApiResponse.NetworkError("Network error")
        val networkError = DeviceOffline<String>("Network error")

        `when`(mockItemDetailModel.getItemDescription(itemId)).thenReturn(networkErrorResult)

        // Then
        itemDetailViewModel.getItemDescription(itemId)

        // Assert
        assertEquals(networkError, itemDetailViewModel.itemDescriptionLiveData.value)
    }
}