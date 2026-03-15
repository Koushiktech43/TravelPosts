package com.android.travelposts.presentation.productdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.travelposts.presentation.productList.ProductViewModel

@Composable
fun ProductDetail(id : Int,viewModel: ProductViewModel) {
    val detail by viewModel.productDetail.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.loadProductDetailByID(id)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        ) {
            Text(detail?.description.toString())
            Spacer(Modifier.height(16.dp))
            Text(detail?.price.toString())
        }
    }
}