package com.example.productcatalog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productcatalog.data.ProductRepository
import com.example.productcatalog.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    // States for product list
    private val _productsState = MutableStateFlow<ProductListState>(ProductListState.Loading)
    val productsState: StateFlow<ProductListState> = _productsState

    // States for product detail
    private val _productDetailState = MutableStateFlow<ProductDetailState>(ProductDetailState.Loading)
    val productDetailState: StateFlow<ProductDetailState> = _productDetailState

    // Load all products
    fun loadProducts() {
        viewModelScope.launch {
            _productsState.value = ProductListState.Loading

            repository.getProducts().fold(
                onSuccess = { products ->
                    if (products.isEmpty()) {
                        _productsState.value = ProductListState.Error("No products available. Check your internet connection.")
                    } else {
                        _productsState.value = ProductListState.Success(products)
                    }
                },
                onFailure = { error ->
                    _productsState.value = ProductListState.Error(error.message ?: "An unexpected error occurred")
                    // Optionally load mock data when API fails
                    // loadMockProducts()
                }
            )
        }
    }

    // Load a specific product by ID
    fun loadProductDetail(productId: Int) {
        viewModelScope.launch {
            _productDetailState.value = ProductDetailState.Loading

            repository.getProductById(productId).fold(
                onSuccess = { product ->
                    _productDetailState.value = ProductDetailState.Success(product)
                },
                onFailure = { error ->
                    _productDetailState.value = ProductDetailState.Error(error.message ?: "An unexpected error occurred")
                }
            )
        }
    }

    // Initialize by loading products
    init {
        Log.d("ProductViewModel", "Initializing ProductViewModel")
        loadMockProducts()
    }

    // States for product list screen
    sealed class ProductListState {
        object Loading : ProductListState()
        data class Success(val products: List<Product>) : ProductListState()
        data class Error(val message: String) : ProductListState()
    }

    // States for product detail screen
    sealed class ProductDetailState {
        object Loading : ProductDetailState()
        data class Success(val product: Product) : ProductDetailState()
        data class Error(val message: String) : ProductDetailState()
    }
    // Add this to ProductViewModel
    private fun loadMockProducts() {
        val mockProducts = List(10) { index ->
            Product(
                id = index + 1,
                title = "Product ${index + 1}",
                description = "This is a mock product description for testing purposes.",
                price = (index + 1) * 9.99,
                discountPercentage = if (index % 2 == 0) 10.0 else 0.0,
                rating = 4.0 + (index % 10) / 10.0,
                stock = 50 + index,
                brand = "MockBrand",
                category = if (index % 3 == 0) "electronics" else if (index % 3 == 1) "clothing" else "home",
                thumbnail = "https://via.placeholder.com/150",
                images = listOf("https://via.placeholder.com/500", "https://via.placeholder.com/500")
            )
        }
        _productsState.value = ProductListState.Success(mockProducts)
    }
}