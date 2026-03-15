package com.android.travelposts

import com.android.travelposts.core.DispatcherProvider
import com.android.travelposts.data.remote.GetProductAPIStatus
import com.android.travelposts.data.remote.Product
import com.android.travelposts.data.remote.ProductDTO
import com.android.travelposts.domain.GetProductUseCase
import com.android.travelposts.presentation.core.UiState
import com.android.travelposts.presentation.productList.ProductListViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsViewModelTest {

    private val useCase : GetProductUseCase = mockk()

    private lateinit var viewModel: ProductListViewModel

    private val dispatcher = StandardTestDispatcher()

    private lateinit var states : MutableList<UiState<List<Product>>>

    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var job : Job


    private val sampleProducts = listOf(
        Product(id = 1, title = "Apple iPhone", category = "Electronics", price = 999.0, description = "", thumbnail = ""),
        Product(id = 2, title = "Samsung TV",   category = "Electronics", price = 499.0, description = "", thumbnail = ""),
    )


    @Before
    fun setUp() {

        Dispatchers.setMain(dispatcher)
        dispatcherProvider = mockk {
            every { io } returns dispatcher
        }
        viewModel = ProductListViewModel(useCase,dispatcherProvider)
        states = mutableListOf()
        job = Job()
        CoroutineScope(dispatcher + job).launch {
            viewModel.uiState.collect {
                states.add(it)
            }
        }

    }

    @After
    fun tearDown() {
        job.cancel()
    }

    @Test
    fun `load posts emits Error`() = runTest(dispatcher) {
        //Arrange
        coEvery { useCase.invoke() } returns GetProductAPIStatus.Error("Error")

        //Act
        viewModel.loadProducts()

        advanceUntilIdle()

        //Assert
        val error = states .last() as UiState.Error
        assertEquals("Error",error.message)
    }

    @Test
    fun `load posts emits success`() = runTest(dispatcher) {
        //Arrange
        coEvery { useCase.invoke() } returns
                GetProductAPIStatus.Success(ProductDTO(products = sampleProducts))

        //Act
        viewModel.loadProducts()

        advanceUntilIdle()

        //Assert
        val success = states .last() as UiState.Success
        assertEquals(success.data,sampleProducts)
        Assert.assertTrue(viewModel.productList.value.isNotEmpty())
    }

}