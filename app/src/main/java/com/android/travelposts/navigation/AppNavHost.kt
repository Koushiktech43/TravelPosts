package com.android.travelposts.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.travelposts.presentation.productList.ProductListScreen
import com.android.travelposts.presentation.productList.ProductViewModel
import com.android.travelposts.presentation.productdetail.ProductDetail

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val viewModel : ProductViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ProductListScreen(viewModel,{
                 navController.navigate("detail/$it")
            })
        }
        composable("detail/{id}", arguments = listOf(
            navArgument("id"){
                type = NavType.IntType
            }
        )) { backStackEntry->
               val id = backStackEntry.arguments?.getInt("id")
               id?.let {
                   ProductDetail(id,viewModel)
               }
        }
    }

}