package com.example.videoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.videoapp.ui.theme.VideoAppTheme
import com.example.videoapp.video.presentation.videoList.VideListScreen
import com.example.videoapp.video.presentation.videoList.VideoViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel:VideoViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle() // screen state


            var refreshing by remember { mutableStateOf(false) }
            LaunchedEffect(refreshing) {
                if (refreshing) {
                    viewModel.loadVideos()
                    refreshing = false
                }
            }
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshing),
                onRefresh = { refreshing = true },
            ) {
                VideoAppTheme {
                    VideListScreen(
                        modifier = Modifier,
                        state = state
                    )
                }

            }

        }
    }
}
