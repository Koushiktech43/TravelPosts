package com.android.travelposts.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.travelposts.presentation.getproducts.GetProductsListScreen
import com.android.travelposts.presentation.getproducts.GetProductsViewModel
import com.android.travelposts.presentation.product_detail.ProductDetailscreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewmodel : GetProductsViewModel = hiltViewModel()

    NavHost(navController, startDestination = "list") {
        composable("list"){
            GetProductsListScreen(viewmodel,{
                navController.navigate("detail/${it.id}")
            })
        }
        composable("detail/{id}",
            arguments = listOf(
                navArgument("id"){
                  type = NavType.IntType
                }
            )
        )
        { backstackEntry ->
            val id = backstackEntry.arguments?.getInt("id")
            val productDetail = id?.let { viewmodel.getProductsById(it) }
            productDetail?.let {
                ProductDetailscreen(it)
            }
        }
    }
}