package com.android.travelposts.presentation.productList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.android.travelposts.data.remote.Product
import com.android.travelposts.presentation.core.UiState

@Composable
fun  ProductListScreen(viewModel: ProductListViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        when(uiState) {
            is UiState.Error -> {
                Text((uiState as UiState.Error).message)
            }
            is UiState.Loading -> {
                Box(
                   modifier = Modifier.fillMaxSize() ,
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items((uiState as UiState.Success<List<Product>>).data) { product->
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        ) {
                            AsyncImage(
                                model = product.thumbnail,
                                contentDescription = product.description,
                                contentScale = ContentScale.Crop
                            )
                            Text(text = product.title)
                        }
                    }
                }
            }
        }
    }
}