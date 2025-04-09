package com.example.productcatalog.di

import android.app.Application
import com.example.productcatalog.data.ProductApi
import com.example.productcatalog.data.ProductRepository
import com.example.productcatalog.data.ProductRepositoryImpl
import com.example.productcatalog.utils.NetworkUtil
import com.example.productcatalog.viewmodel.ProductViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
val appModule = module {
    // Network Utility
    single { NetworkUtil(get()) }

    // API Service
    single { ProductApi.create() }

    // Repository
    single<ProductRepository> { ProductRepositoryImpl(get()) }

    // ViewModels
    viewModel { ProductViewModel(get()) }
}
class ProductCatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@ProductCatalogApplication)
            modules(appModule)
        }
    }
}

// Define all dependencies
