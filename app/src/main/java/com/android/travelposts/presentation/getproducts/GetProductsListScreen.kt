package com.android.travelposts.presentation.getproducts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.travelposts.data.remote.ProductDTO
import com.android.travelposts.presentation.getproducts.utils.UiState


@Composable
fun GetProductsListScreen(viewModel: GetProductsViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when(uiState) {
            is UiState.Error -> TODO()
            UiState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                LazyColumn() {
                    items(((uiState as UiState.Success<List<ProductDTO>>).data)){ list->
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            Text(text = list.title)
                        }
                    }
                }
            }
        }
    }

}