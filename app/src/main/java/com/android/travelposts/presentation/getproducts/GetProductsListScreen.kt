package com.android.travelposts.presentation.getproducts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.android.travelposts.R
import com.android.travelposts.presentation.getproducts.utils.UiState


@Composable
fun GetProductsListScreen(viewModel: GetProductsViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val filteredList by viewModel.filteredList.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {viewModel.updateSearch(query = it)},
            placeholder = {Text(stringResource(R.string.search_products))},
            modifier = Modifier.padding(24.dp)
        )
        /**
         * Category Buttons
         */
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            items(categories){ category->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {viewModel.selectCategory(category)},
                    label = {Text(category)},
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        when(uiState) {
            is UiState.Error -> Text(text = (uiState as UiState.Error).message)
            UiState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredList){ list->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            AsyncImage(
                                model = list.image,
                                contentDescription = list.title,
                                contentScale = ContentScale.Crop
                            )
                            Text(text = list.title)
                        }
                    }
                }
            }
        }
    }

}