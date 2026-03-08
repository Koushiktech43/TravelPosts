package com.android.travelposts.presentation.product_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.travelposts.data.remote.ProductDTO

@Composable
fun ProductDetailscreen(detail : ProductDTO) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Column(
           verticalArrangement = Arrangement.Center
        ) {
            Text(text =detail.id.toString())
            Spacer(modifier = Modifier.height(16.dp))
            Text(text =detail.description)
        }

    }
}