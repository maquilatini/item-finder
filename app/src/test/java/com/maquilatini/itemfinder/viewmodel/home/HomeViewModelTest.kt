package com.maquilatini.itemfinder.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maquilatini.itemfinder.MainDispatcherRule
import com.maquilatini.itemfinder.api.ApiResponse
import com.maquilatini.itemfinder.api.model.Category
import com.maquilatini.itemfinder.model.categories.ICategoriesModel
import com.maquilatini.itemfinder.observeOnce
import com.maquilatini.itemfinder.viewmodel.DeviceOffline
import com.maquilatini.itemfinder.viewmodel.ErrorResponse
import com.maquilatini.itemfinder.viewmodel.Loading
import com.maquilatini.itemfinder.viewmodel.SuccessResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class HomeViewModelTest {

    private lateinit var mockCategoriesModel: ICategoriesModel
    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockCategoriesModel = mock(ICategoriesModel::class.java)
        homeViewModel = HomeViewModel(mockCategoriesModel)
    }

    @Test
    fun test_getCategories_with_success_response() = runTest {
        // Given
        val mockSuccessResponse = ApiResponse.Success(listOf(Category("MLA1", "Category 1"), Category("MLA2", "Category 2")))
        `when`(mockCategoriesModel.getCategories(anyString())).thenReturn(mockSuccessResponse)

        // Then
        homeViewModel.getCategories("MLA")

        // Assert - Check the categoriesLiveData was set with the SuccessResponse
        homeViewModel.categoriesLiveData.observeOnce { response ->
            when (response) {
                is SuccessResponse -> {
                    assertTrue(response.data.isNotEmpty())
                    assertEquals(2, response.data.size)
                    assertNotNull(homeViewModel.getLoadedCategories())
                }
                else -> fail("The response should be SuccessResponse")
            }
        }
    }

    @Test
    fun test_getCategories_with_error_response() = runTest {
        // Given
        val mockErrorResponse = ApiResponse.Error(404, "Not Found")
        `when`(mockCategoriesModel.getCategories(anyString())).thenReturn(mockErrorResponse)

        // Then
        homeViewModel.getCategories("MLA")

        // Assert - Verify the categoriesLiveData was set with the ErrorResponse
        homeViewModel.categoriesLiveData.observeOnce { response ->
            when (response) {
                is ErrorResponse -> {
                    assertEquals(mockErrorResponse.code, response.code)
                    assertEquals(mockErrorResponse.message, response.message)
                    assertTrue(homeViewModel.getLoadedCategories().isEmpty())
                }
                else -> fail("The response should be ErrorResponse")
            }
        }
    }

    @Test
    fun test_getLoadedCategories_successful() = runTest {
        // Given
        val mockedCategoriesList = listOf(Category("MLA1", "Category Name 1"), Category("MLA2", "Category Name 2"))
        val mockResponse = ApiResponse.Success(mockedCategoriesList)
        `when`(mockCategoriesModel.getCategories(anyString())).thenReturn(mockResponse)
        assertTrue(homeViewModel.getLoadedCategories().isEmpty())

        // Then
        homeViewModel.getCategories("MLA")

        // Assert
        assertEquals(2, homeViewModel.getLoadedCategories().size)
        assertEquals(mockedCategoriesList[0], homeViewModel.getLoadedCategories()[0])
        assertEquals(mockedCategoriesList[1], homeViewModel.getLoadedCategories()[1])
    }

    @Test
    fun test_getCategories_should_call_method_of_ICategoriesModel() = runTest {
        // Given
        val siteId = "MLA"

        // Then
        homeViewModel.getCategories(siteId)

        // Assert
        verify(mockCategoriesModel).getCategories(siteId)
    }

    @Test
    fun test_getCategories_should_set_Loading_state_before_API_call() {
        // Given
        val siteId = "MLA"
        val loadingState = Loading<Boolean>(true)

        // Then
        homeViewModel.getCategories(siteId)

        // Assert
        assertEquals(loadingState, homeViewModel.categoriesLiveData.value)
    }

    @Test
    fun test_getCategories_should_set_SuccessResponse_state_when_API_call_is_successful() = runTest {
        // Given
        val siteId = "MLA"
        val categories = listOf(Category("1", "Category 1"), Category("2", "Category 2"))
        val successResult = ApiResponse.Success(categories)
        `when`(mockCategoriesModel.getCategories(siteId)).thenReturn(successResult)

        // Then
        homeViewModel.getCategories(siteId)

        // Assert
        assertEquals(SuccessResponse(categories), homeViewModel.categoriesLiveData.value)
    }

    @Test
    fun test_getCategories_should_set_ErrorResponse_state_when_API_call_returns_error() = runTest {
        // Given
        val siteId = "MLA"
        val errorResult = ApiResponse.Error(404, "Not Found")
        val errorResponse = ErrorResponse<String>(404, "Not Found")

        `when`(mockCategoriesModel.getCategories(siteId)).thenReturn(errorResult)

        // Then
        homeViewModel.getCategories(siteId)

        // Assert
        assertEquals(errorResponse, homeViewModel.categoriesLiveData.value)
    }

    @Test
    fun test_getCategories_should_set_DeviceOffline_state_when_there_is_a_network_error() = runTest {
        // Given
        val siteId = "123"
        val networkErrorResult = ApiResponse.NetworkError("Network error")
        val networkError = DeviceOffline<String>("Network error")

        `when`(mockCategoriesModel.getCategories(siteId)).thenReturn(networkErrorResult)

        // Then
        homeViewModel.getCategories(siteId)

        // Assert
        assertEquals(networkError, homeViewModel.categoriesLiveData.value)
    }
}
