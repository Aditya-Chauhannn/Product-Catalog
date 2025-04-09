package com.example.productcatalog.data

import android.util.Log
import com.example.productcatalog.data.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getProductById(id: Int): Result<Product>
}

class ProductRepositoryImpl(private val api: ProductApi) : ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            Log.d("ProductRepository", "Fetching products from API")
            val response = api.getProducts()
            Log.d("ProductRepository", "Successfully fetched ${response.products.size} products")
            Result.success(response.products)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching products: ${e.message}", e)
            // Return an empty list instead of failing completely
            Result.success(emptyList())
            // Or keep the failure: Result.failure(e)
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> = withContext(Dispatchers.IO) {
        try {
            val product = api.getProductById(id)
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}