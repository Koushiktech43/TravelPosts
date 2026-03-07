package com.android.travelposts.presentation.listposts


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.travelposts.R
import com.android.travelposts.presentation.common.UiState


@Composable
fun GetListPostScreen(viewModel: GetTravelPostViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val filteredList by viewModel.filteredList.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {viewModel.updateSearchQuery(it)},
            placeholder = { Text(stringResource(R.string.search_products)) },
            modifier = Modifier.padding(24.dp)

        )

        when(uiState) {
            is UiState.Error -> TODO()
            UiState.Initial, UiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ){
                    items(filteredList){ item ->
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),

                            ) {
                            Text(text = item.data.name)
                        }
                    }
                }
            }
        }
    }



}