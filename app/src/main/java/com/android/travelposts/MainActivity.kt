package com.android.travelposts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.travelposts.presentation.listposts.GetListPostScreen
import com.android.travelposts.presentation.listposts.GetTravelPostViewModel
import com.android.travelposts.ui.theme.TravelPostsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelPostsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RedditPosts(
                        viewModel = hiltViewModel(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RedditPosts(viewModel: GetTravelPostViewModel, modifier: Modifier = Modifier) {
    GetListPostScreen(viewModel)
}

@Preview(showBackground = true)
@Composable
fun RedditPostsPreview() {
   /* TravelPostsTheme {
        RedditPosts("Android")
    }*/
}