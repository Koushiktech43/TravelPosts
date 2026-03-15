package com.android.travelposts.presentation.productList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.android.travelposts.presentation.core.UiState

@Composable
fun ProductListScreen(viewModel: ProductViewModel, onProductClicked : (Int) -> Unit) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filteredProductList by viewModel.filteredProductList.collectAsStateWithLifecycle()
    val categoryList by viewModel.categoryList.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            placeholder = { Text("Search..") }
        )
        Row {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                items(categoryList) { currentCategory ->
                    FilterChip(
                        selected = currentCategory == selectedCategory,
                        onClick = { viewModel.setSelectedCategory(currentCategory) },
                        label = { Text(currentCategory) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
        when (uiState) {
            is UiState.Error -> {
                Text((uiState as UiState.Error).message)
            }

            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(filteredProductList) { product ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onProductClicked(product.id)}
                                .padding(8.dp)
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