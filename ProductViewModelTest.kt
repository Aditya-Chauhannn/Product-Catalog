package com.example.productcatalog.viewmodel

import com.example.productcatalog.data.ProductRepository
import com.example.productcatalog.data.models.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductViewModelTest {

    private lateinit var viewModel: ProductViewModel
    private lateinit var repository: ProductRepository
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    // Sample test data
    private val sampleProducts = listOf(
        Product(
            id = 1,
            title = "iPhone 9",
            description = "An apple mobile which is nothing like apple",
            price = 549.0,
            discountPercentage = 12.96,
            rating = 4.69,
            stock = 94,
            brand = "Apple",
            category = "smartphones",
            thumbnail = "https://example.com/thumbnail.jpg",
            images = listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg")
        ),
        Product(
            id = 2,
            title = "iPhone X",
            description = "SIM-Free, Model A19211 6.5-inch Super Retina HD display",
            price = 899.0,
            discountPercentage = 17.94,
            rating = 4.44,
            stock = 34,
            brand = "Apple",
            category = "smartphones",
            thumbnail = "https://example.com/thumbnail2.jpg",
            images = listOf("https://example.com/image3.jpg", "https://example.com/image4.jpg")
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = ProductViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProducts should update state to Success when repository returns products`() = runTest {
        // Given
        coEvery { repository.getProducts() } returns Result.success(sampleProducts)

        // When
        viewModel.loadProducts()

        // Then
        val state = viewModel.productsState.value
        assertTrue(state is ProductViewModel.ProductListState.Success)
        assertEquals(sampleProducts, (state as ProductViewModel.ProductListState.Success).products)
    }

    @Test
    fun `loadProducts should update state to Error when repository returns failure`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { repository.getProducts() } returns Result.failure(Exception(errorMessage))

        // When
        viewModel.loadProducts()

        // Then
        val state = viewModel.productsState.value
        assertTrue(state is ProductViewModel.ProductListState.Error)
        assertEquals(errorMessage, (state as ProductViewModel.ProductListState.Error).message)
    }

    @Test
    fun `loadProductDetail should update state to Success when repository returns product`() = runTest {
        // Given
        val productId = 1
        val product = sampleProducts.first()
        coEvery { repository.getProductById(productId) } returns Result.success(product)

        // When
        viewModel.loadProductDetail(productId)

        // Then
        val state = viewModel.productDetailState.value
        assertTrue(state is ProductViewModel.ProductDetailState.Success)
        assertEquals(product, (state as ProductViewModel.ProductDetailState.Success).product)
    }

    @Test
    fun `loadProductDetail should update state to Error when repository returns failure`() = runTest {
        // Given
        val productId = 1
        val errorMessage = "Product not found"
        coEvery { repository.getProductById(productId) } returns Result.failure(Exception(errorMessage))

        // When
        viewModel.loadProductDetail(productId)

        // Then
        val state = viewModel.productDetailState.value
        assertTrue(state is ProductViewModel.ProductDetailState.Error)
        assertEquals(errorMessage, (state as ProductViewModel.ProductDetailState.Error).message)
    }
}