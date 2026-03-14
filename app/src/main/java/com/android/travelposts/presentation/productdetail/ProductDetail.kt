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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.travelposts.data.remote.Product

@Composable
fun ProductDetail(detail : Product) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        ) {
            Text(detail.description)
            Spacer(Modifier.height(16.dp))
            Text(detail.price.toString())
        }
    }

}