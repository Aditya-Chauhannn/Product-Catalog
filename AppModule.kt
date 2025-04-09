package com.example.productcatalog.di

import android.app.Application
import com.example.productcatalog.data.ProductApi
import com.example.productcatalog.data.ProductRepository
import com.example.productcatalog.data.ProductRepositoryImpl
import com.example.productcatalog.utils.NetworkUtil
import com.example.productcatalog.viewmodel.ProductViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
class AppModule {
    val appModule = module {
        single { NetworkUtil(get()) }
        single { ProductApi.create() }
        single<ProductRepository> { ProductRepositoryImpl(get()) }
        viewModel { ProductViewModel(get()) }
    }
}