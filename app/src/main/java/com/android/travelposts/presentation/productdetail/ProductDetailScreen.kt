package com.android.travelposts.presentation.productdetail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.travelposts.R
import com.android.travelposts.data.remote.ProductDTO


@Composable
fun ProductDetailScreen(detail : ProductDTO) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
       val cartText =  stringResource(R.string.cart_added)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            ) {
            Text(detail.id.toString())
            Text(text = detail.category)
            Text(text = detail.title)
            Button(onClick = {
                Toast.makeText(context, cartText, Toast.LENGTH_LONG).show()
            }) {
                Text(stringResource(R.string.add_to_cart))
            }
        }
    }
}