package com.example.productcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.productcatalog.ui.ProductDetailScreen
import com.example.productcatalog.ui.ProductListScreen
import com.example.productcatalog.ui.theme.ProductCatalogTheme
import com.example.productcatalog.viewmodel.ProductViewModel
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductCatalogTheme {
                ProductCatalogApp()
            }
        }
    }
}

@Composable
fun ProductCatalogApp() {
    val navController = rememberNavController()
    val viewModel: ProductViewModel = getViewModel()

    NavHost(navController = navController, startDestination = "productList") {
        composable("productList") {
            ProductListScreen(
                viewModel = viewModel,
                onProductClick = { productId ->
                    navController.navigate("productDetail/$productId")
                }
            )
        }

        composable(
            route = "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                viewModel = viewModel,
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}