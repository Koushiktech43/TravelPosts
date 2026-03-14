package com.android.travelposts.presentation.productDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.travelposts.data.remote.ProductDTO


@Composable
fun ProductDetailScreen(detail : ProductDTO) {

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center

    ) {
        Column(
            modifier = Modifier.padding(16.dp),

        ) {
            Text(detail.id.toString())
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = detail.category)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = detail.price.toString())
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {}) {
                Text("Add to Cart")
            }
        }

    }
}