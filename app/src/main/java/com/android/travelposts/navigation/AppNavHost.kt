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
import com.android.travelposts.presentation.productDetails.ProductDetailScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewModel : GetProductsViewModel = hiltViewModel()
    NavHost(
        navController,
        startDestination = "list"
    ) {
        composable("list") {
            GetProductsListScreen(
                viewModel,{
                    navController.navigate("detail/${it}")
                }
            )
        }
        composable("detail/{id}",
            arguments = listOf(
                navArgument(
                    "id"
                ){
                    type = NavType.IntType
                }
            )
            ) {  backStackEntry->
               val id =  backStackEntry.arguments?.getInt("id")
              id?.let {
                  val detail = viewModel.getProductByID(id)
                  detail?.let { it1 -> ProductDetailScreen(it1) }
              }
        }
    }
}