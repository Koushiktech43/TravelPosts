package com.android.travelposts.presentation.listposts

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.travelposts.data.remote.TravelPostDto
import com.android.travelposts.presentation.common.UiState


@Composable
fun GetListPostScreen(viewModel: GetTravelPostViewModel){

    val travelPostState by viewModel.uiSate.collectAsStateWithLifecycle()

    Column(
       modifier = Modifier.fillMaxSize()
    ) {
        when(travelPostState){
            is UiState.Error -> TODO()
            UiState.Initial , UiState.Loading -> {
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.Center

                ) {
                    val listOfData = (travelPostState as UiState.Success<TravelPostDto>).data.data.children
                    items(listOfData){ item->
                        Text(text =item.data.name)
                    }
                }
            }
        }

    }

}